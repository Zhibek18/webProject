package kz.kakimzhanova.delivery.dao.impl;

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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrderDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    @BeforeClass
    public static void init(){
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, e);
        }
    }
    @AfterClass
    public static void dispose() {
        try {
            ConnectionPool.getInstance().dispose();
       } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }

    @Test
    public void findById() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order expected = new Order(149, "zhibek", null);
        Order actual = new Order();
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            logger.log(Level.DEBUG, orderDao);
            actual = orderDao.findById(149);
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
            Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
        }
    }


    @Test
    public void create() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order expected = new Order(150,"user",null);
        Order actual = null;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            actual = orderDao.create("user");
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
    }

    @Test
    public void delete() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean expected = true;
        boolean actual = false;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            actual = orderDao.delete(150);
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }
}