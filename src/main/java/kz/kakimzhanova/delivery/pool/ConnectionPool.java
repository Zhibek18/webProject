package kz.kakimzhanova.delivery.pool;

import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger();
    private static final int DEFAULT_POOL_SIZE = 5;
    private static Lock lock = new ReentrantLock();
    private static ConnectionPool instance = null;
    private static AtomicBoolean created = new AtomicBoolean(false);

    private BlockingQueue<Connection> freeConnectionQueue;
    private BlockingQueue<Connection> givenAwayConnectionQueue;

    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driver = dbResourceManager.getValue(DBParameter.DB_DRIVER.getName());
        this.url = dbResourceManager.getValue(DBParameter.DB_URL.getName());
        this.user = dbResourceManager.getValue(DBParameter.DB_USER.getName());
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD.getName());
        try {
            this.poolSize = Integer.valueOf(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE.getName()));
        }catch (NumberFormatException e){
            poolSize = DEFAULT_POOL_SIZE;
        }
    }

    public void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
        freeConnectionQueue = new ArrayBlockingQueue<>(poolSize);
        givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                freeConnectionQueue.add(connection);
            } catch ( SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
        int connectionsCount = freeConnectionQueue.size();
        if (connectionsCount == DEFAULT_POOL_SIZE) {
            logger.log(Level.INFO, "Connection pool initialized");
        } else {
            logger.log(Level.ERROR, connectionsCount + " connections out of " + poolSize + " in Connection pool were initialized");
        }
    }

    public Connection takeConnection() throws InterruptedException {
        Connection connection = freeConnectionQueue.take();
        givenAwayConnectionQueue.add(connection);
        return connection;
    }

    public boolean returnConnection(Connection connection){
        boolean isReturned = false;
        if (connection != null) {
            freeConnectionQueue.add(connection);
            isReturned = givenAwayConnectionQueue.remove(connection);
        }
        return isReturned;
    }
    public void dispose() throws ConnectionPoolException {
        clearConnectionQueues();
        logger.log(Level.INFO, "Connection pool disposed");
    }

    private void clearConnectionQueues() throws ConnectionPoolException {
        closeConnectionQueue(freeConnectionQueue);
        closeConnectionQueue(givenAwayConnectionQueue);
    }
    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws ConnectionPoolException {
        Connection connection;
        while ((connection = queue.poll()) != null){
            try{
                if (!connection.getAutoCommit()){
                    connection.commit();
                }
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException(e);
            }
        }
    }
    public static ConnectionPool getInstance() {
        if (!created.get()) {
            lock.lock();
            try{
                if (instance == null) {
                    instance = new ConnectionPool();
                    created.set(true);
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

}
