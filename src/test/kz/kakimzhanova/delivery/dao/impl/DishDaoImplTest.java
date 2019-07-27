package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class DishDaoImplTest {
    private static final Logger logger = LogManager.getLogger();
    private static DishDao dishDao;
    @BeforeClass
    public static void init(){
        dishDao = new DishDaoImpl();
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }


    @Test
    public void findById() {
        Dish expected = new Dish("rice", BigDecimal.valueOf(400.0));
        Dish actual = new Dish();
        try {
            actual = dishDao.findById("rice");
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
            actual = dishDao.create(new Dish("spaghetti", BigDecimal.valueOf(13.5)));
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
            actual = dishDao.delete("spaghetti");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }



    @Test
    public void updatePrice() {
        boolean expected = true;
        boolean actual = false;
        try{
            actual = dishDao.updatePrice("rice", new BigDecimal(400.0));
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }
}