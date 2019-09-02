package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.entity.OrderedDish;
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

import java.math.BigDecimal;

public class OrderListDaoImplTest {
    private static Logger logger = LogManager.getLogger();

    @BeforeClass
    public static void init(){
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
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
    public void findByIdAndDishName() {

        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        OrderedDish expected = new OrderedDish(146, "rice", "Рис", "Rice", null, null, BigDecimal.valueOf(1.1), 1);
        OrderedDish actual = new OrderedDish();
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            actual = orderListDao.findByOrderIdAndDishName(146, "rice");
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.WARN, e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        Assert.assertEquals(expected.toString().trim(), actual.toString().trim());
    }


    @Test
    public void create() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean expected = true;
        boolean actual = false;
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            actual = orderListDao.create(149, "friedPotato", 1);
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.WARN, e);
        }finally {
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
        boolean expected = true;
        boolean actual = false;
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            actual = orderListDao.delete(149, "friedPotato");
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.WARN, e);
        }finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, e);
            }
        }
        Assert.assertEquals(expected, actual);
    }
}