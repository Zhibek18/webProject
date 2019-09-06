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
        Dish expected = new Dish("rice","Рис", "Rice", "Вареный рис", "Boiled rice", BigDecimal.valueOf(400.00));
        Dish actual = new Dish();
        try {
            actual = dishDao.findById("rice");
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void create() {
        Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
        Dish expected = dish;
        Dish actual = new Dish();
        try {
            dishDao.create(dish);
            actual = dishDao.findById("spaghetti");//tested
            dishDao.delete("spaghetti");//tested
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Dish actual = new Dish();
        Dish dish = new Dish("spaghetti","Спагетти", "Spagetti", "Вареные спагетти","Boiled spagetti", BigDecimal.valueOf(13.50));
        try {
            dishDao.create(dish);//tested
            dishDao.delete("spaghetti");
            actual = dishDao.findById("spaghetti");//tested
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(actual);
    }



    @Test
    public void update() {
        Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
        Dish expected = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(15.00));
        Dish actual = new Dish();
        try{
            dishDao.create(dish);//tested
            Dish updatedDish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(15));
            actual = dishDao.update(updatedDish);//update dish in db
            dishDao.delete("spaghetti");
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }
}