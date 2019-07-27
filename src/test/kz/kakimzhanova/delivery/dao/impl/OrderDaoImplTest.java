package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrderDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static OrderDao orderDao;
    @BeforeClass
    public static void init(){
        orderDao = new OrderDaoImpl();
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }

    @Test
    public void findById() {
        Order expected = new Order(1, "user", null);
        Order actual = new Order();
        try {
            actual = orderDao.findById(1);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected.getLogin().trim(), actual.getLogin().trim());
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
        Order expected = new Order(1,"user",null);
        Order actual = null;
        try {
             actual = orderDao.create("user");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
    }

    @Test
    public void delete() {
        boolean expected = true;
        boolean actual = false;
        try {
            actual = orderDao.delete(4);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }
}