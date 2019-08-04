package kz.kakimzhanova.delivery.pool;

import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool provides dao with connections
 */
public class ConnectionPool {
    private static Logger logger = LogManager.getLogger();
    private static final int DEFAULT_POOL_SIZE = 5;
    private static Lock lock = new ReentrantLock();
    private static ConnectionPool instance = null;
    private static AtomicBoolean created = new AtomicBoolean(false);

    private BlockingQueue<Connection> freeConnectionQueue;
    private List<Connection> givenAwayConnectionList;

    private static String driverName;
    private static String url;
    private static String user;
    private static String password;
    private static int poolSize;
    static {
        DbResourceManager dbResourceManager = DbResourceManager.getInstance();
        driverName = dbResourceManager.getString(DbParameter.DB_DRIVER.getName());
        url = dbResourceManager.getString(DbParameter.DB_URL.getName());
        user = dbResourceManager.getString(DbParameter.DB_USER.getName());
        password = dbResourceManager.getString(DbParameter.DB_PASSWORD.getName());
        try {
            poolSize = Integer.valueOf(dbResourceManager.getString(DbParameter.DB_POOL_SIZE.getName()));
        }catch (NumberFormatException e){
            logger.log(Level.WARN, e);
            poolSize = DEFAULT_POOL_SIZE;
        }
    }
    private ConnectionPool() { }
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

    /**
     * Connection pool initialization, poolSize connections are received from driver and saved in freeGivenConnectionQueue
     * @throws ConnectionPoolException
     */
    public void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Driver not found" + e);
        }
        freeConnectionQueue = new ArrayBlockingQueue<>(poolSize);
        givenAwayConnectionList = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
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

    /**
     * takes connection from freeConnectionQueue, saves it in givenAwayConnectionList
     * @return Connection from pool
     */
    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = freeConnectionQueue.take();
            givenAwayConnectionList.add(connection);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * receives connection and saves it in freeConnectionQueue
     * @param connection
     * @return
     */
    public boolean returnConnection(Connection connection){
        boolean isReturned = false;
        try {
            if (connection != null) {
                if (!connection.getAutoCommit()){
                    connection.setAutoCommit(true);
                }
                freeConnectionQueue.put(connection);
                isReturned = givenAwayConnectionList.remove(connection);
            }
        }catch (InterruptedException e){
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            logger.log(Level.WARN, e);
        }
        return isReturned;
    }
    public void dispose() throws ConnectionPoolException {
        clearConnectionQueues();
        deregisterDriver();
        logger.log(Level.INFO, "Connection pool disposed");
    }

    /**
     * disposes Connection pool, closes all connections
     * @throws ConnectionPoolException
     */
    private void clearConnectionQueues() throws ConnectionPoolException {
        closeConnectionQueue(freeConnectionQueue);
        closeConnectionList(givenAwayConnectionList);
    }

    private void closeConnectionList(List <Connection> list) throws ConnectionPoolException {
        for (Connection connection : list){
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
    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws ConnectionPoolException {
        Connection connection;
        while ((connection = queue.poll()) != null){
            try{
                if (!connection.getAutoCommit()){
                    connection.commit();
                }
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }

    private void deregisterDriver(){
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }
}
