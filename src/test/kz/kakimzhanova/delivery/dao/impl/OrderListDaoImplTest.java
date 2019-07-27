package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrderListDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static OrderListDao orderListDao;
    @BeforeClass
    public static void init(){
        orderListDao = new OrderListDaoImpl();
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }
    @Test
    public void findByIdAndDishName() {
        OrderedDish expected = new OrderedDish(1, "rice", 1);
        OrderedDish actual = new OrderedDish();
        try {
            actual = orderListDao.findByOrderIdAndDishName(1, "rice");

        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected.toString().trim(), actual.toString().trim());
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
    public void create() {
        boolean expected = true;
        boolean actual = false;
        try {
            actual = orderListDao.create(1, "fried chicken");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        boolean expected = true;
        boolean actual = false;
        try {
            actual = orderListDao.delete(1, "fried potato");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void updateQuantity(){
        boolean expected = true;
        boolean actual = false;
        try {
            actual = orderListDao.quantityIncrement(1, "rice");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }
}