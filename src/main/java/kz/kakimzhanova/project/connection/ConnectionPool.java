package kz.kakimzhanova.project.connection;

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

    private ConnectionPool(){
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driver = dbResourceManager.getValue(DBParameter.DB_DRIVER.getName());
        this.url = dbResourceManager.getValue(DBParameter.DB_URL.getName());
        this.user = dbResourceManager.getValue(DBParameter.DB_USER.getName());
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD.getName());
        try {
            this.poolSize = Integer.valueOf(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE.getName()));
        }catch (NumberFormatException e){
            poolSize = 5;
        }
        initPoolData();
    }

    public void initPoolData(){
        try {
            Class.forName(driver);
            freeConnectionQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(true);
                freeConnectionQueue.add(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.WARN, e);
        }
    }

    public Connection takeConnection() throws InterruptedException {
        Connection connection = freeConnectionQueue.take();
        givenAwayConnectionQueue.add(connection);
        return connection;
    }

    public boolean returnConnection(Connection connection){
        if (connection != null) {
            freeConnectionQueue.add(connection);
            return givenAwayConnectionQueue.remove(connection);
        }
        return false;
    }
    public void dispose(){
        clearConnectionQueues();
    }

    private void clearConnectionQueues(){
        closeConnectionQueue(freeConnectionQueue);
        closeConnectionQueue(givenAwayConnectionQueue);
    }
    private void closeConnectionQueue(BlockingQueue<Connection> queue){
        try{
            Connection connection;
            while ((connection = queue.poll()) != null){
                if (!connection.getAutoCommit()){
                    connection.commit();
                }
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e);
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
            }catch (Exception e){ //specify exception!!!
                logger.log(Level.WARN, e);
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

}
