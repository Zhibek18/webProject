package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.math.BigDecimal;

public class DishDaoImplTest {
    private static final Logger logger = LogManager.getLogger();
    private static DishDao dishDao;
    private static Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
    private static Dish anotherDish = new Dish("pasta","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(13.50));
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

    @Before
    public void setUp() throws Exception {
        dishDao.create(dish);
    }

    @After
    public void tearDown() throws Exception {
        dishDao.delete(dish.getDishName());
        dishDao.delete(anotherDish.getDishName());
    }

    @Test
    public void findById() {
        Dish expected = dish;
        Dish actual = new Dish();
        try {
            actual = dishDao.findById(dish.getDishName());
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void create() {
        Dish expected = anotherDish;
        Dish actual = new Dish();
        try {
            dishDao.create(anotherDish);
            actual = dishDao.findById(anotherDish.getDishName());//tested
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Dish actual = new Dish();
        try {
            dishDao.delete("spaghetti");
            actual = dishDao.findById("spaghetti");//tested
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(actual);
    }



    @Test
    public void update() {
        Dish expected = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(15.00));
        Dish actual = new Dish();
        try{
            Dish updatedDish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(15));
            actual = dishDao.update(updatedDish);//update dish in db
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }
}