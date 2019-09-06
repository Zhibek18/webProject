package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManager;
import kz.kakimzhanova.delivery.transaction.impl.OrderTransactionManagerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.util.NoSuchElementException;

public class OrderDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static UserDao userDao = new UserDaoImpl();
    private static User testUser = new User("testUser", "test1", "test", "test", "test", 1, "123456");
    private static Order order = null;
    @BeforeClass
    public static void init(){
        try {
            ConnectionPool.getInstance().initPoolData();
            userDao.create(testUser);
            order = createOrder();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, e);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Test user was not created: " + e);
        } catch (TransactionManagerException e) {
            logger.log(Level.ERROR, e);
        }
    }
    private static Order createOrder () throws TransactionManagerException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order order = null;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            order = orderDao.create(testUser.getLogin());
            transactionManager.commit();

        } catch (TransactionManagerException | DaoException e) {
            logger.log(Level.ERROR, e);
        }finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        if (order != null) {
            return order;
        } else throw new TransactionManagerException("Order for test user was not created");
    }


    @AfterClass
    public static void dispose() {
        try {
            userDao.delete(testUser.getLogin());
            if (!deleteOrder()){
                logger.log(Level.WARN, "Order " + order.getOrderId() + " was not deleted");
            }
            ConnectionPool.getInstance().dispose();
       } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Test user was not deleted: " + e);
        }
    }
    private static boolean deleteOrder(){
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isDeleted = false;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            isDeleted = orderDao.delete(order.getOrderId());
            transactionManager.commit();

        } catch (DaoException | TransactionManagerException e) {
            logger.log(Level.ERROR, e);
        }finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        return isDeleted;
    }

    @Test
    public void findById() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order expected = order;
        Order actual = null;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            logger.log(Level.DEBUG, orderDao);
            actual = orderDao.findById(order.getOrderId());
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        }finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        logger.log(Level.DEBUG, actual);
        if (actual != null) {
            Assert.assertEquals(expected, actual);
        }
    }


    @Test
    public void create() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order actual = null;
        Order expected = null;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            expected = orderDao.create(testUser.getLogin());
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        }finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        try {
            if (expected != null) {
                transactionManager.beginTransaction();
                OrderDao orderDao = transactionManager.connectOrderDao();
                actual = orderDao.findById(expected.getOrderId());
                transactionManager.commit();
            } else {
                throw new TransactionManagerException("Got null pointer instead of expected order");
            }
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order actual = null;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            orderDao.delete(order.getOrderId());
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            actual = orderDao.findById(order.getOrderId());
            transactionManager.commit();

        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        }finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        Assert.assertNull(actual);
    }
}