package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.dao.OrderListDao;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.exception.ConnectionPoolException;
import kz.kakimzhanova.project.exception.DaoException;
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