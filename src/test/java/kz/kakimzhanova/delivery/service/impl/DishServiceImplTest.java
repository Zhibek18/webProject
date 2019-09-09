package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.dao.impl.DishDaoImpl;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.service.DishService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class DishServiceImplTest {
    private static final Logger logger = LogManager.getLogger();
    private static DishDao dishDao;
    private static Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
    private static Dish anotherDish = new Dish("pasta","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(13.50));
    private static Dish thirdDish = new Dish("makaroni","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(16));
    private static DishService dishService = new DishServiceImpl();
    @BeforeClass
    public static void init(){
        dishDao = new DishDaoImpl();
        try {
            ConnectionPool.getInstance().initPoolData();
            dishDao.create(dish);
            dishDao.create(anotherDish);
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, e);
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @AfterClass
    public static void dispose() {
        try {
            dishDao.delete(dish.getDishName());
            dishDao.delete(anotherDish.getDishName());
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Test
    public void testFindAllDishes() {
        List<Dish> dishes = null;
        try {
            dishes = dishService.findAllDishes();
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(dishes);
    }
    @Test
    public void testAddDish() {
        Dish expected = thirdDish;
        Dish actual = null;
        try {
            dishService.addDish(thirdDish);
            actual = dishService.findById(thirdDish.getDishName());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testDelete() {
        Dish actual = dish;
        try {
            dishService.delete(dish.getDishName());
            actual = dishService.findById(dish.getDishName());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(actual);
    }

    @Test
    public void testEditDish() {
        Dish expected = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(130));
        Dish actual = null;
        try {
            dishService.editDish(expected);
            actual = dishService.findById(expected.getDishName());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(actual, expected);
    }
}