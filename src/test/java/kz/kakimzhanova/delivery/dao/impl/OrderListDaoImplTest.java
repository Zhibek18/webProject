package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.User;
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
    private static UserDao userDao = new UserDaoImpl();
    private static User testUser = new User("testUser", "test1", "test", "test", "test", 1, "123456");
    private static Order order = null;
    private static DishDao dishDao = new DishDaoImpl();
    private static Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
    private static Dish anotherDish = new Dish("pasta","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(13.50));
    private static Dish thirdDish = new Dish("makaroni","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(16));
    private final static int DISH_QUANTITY = 1;
    @BeforeClass
    public static void init(){
        try {
            ConnectionPool.getInstance().initPoolData();
            userDao.create(testUser);
            dishDao.create(dish);
            dishDao.create(anotherDish);
            dishDao.create(thirdDish);
            order = createOrder();
        } catch (ConnectionPoolException | TransactionManagerException | DaoException e) {
            logger.log(Level.ERROR, e);
        }
    }
    private static Order createOrder () throws TransactionManagerException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order order = null;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            order = orderDao.create(testUser.getLogin());
            orderListDao.create(order.getOrderId(),dish.getDishName(),DISH_QUANTITY );
            orderListDao.create(order.getOrderId(), thirdDish.getDishName(), DISH_QUANTITY);
            transactionManager.commit();
        } catch (TransactionManagerException | DaoException e) {
            logger.log(Level.ERROR, e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.ERROR, e);
            }
        }
        if (order != null) {
            return order;
        } else throw new TransactionManagerException("Order for test user was not created");
    }
    @AfterClass
    public static void dispose() {
        try {
            deleteOrder();
            userDao.delete(testUser.getLogin());
            dishDao.delete(dish.getDishName());
            dishDao.delete(anotherDish.getDishName());
            dishDao.delete(thirdDish.getDishName());
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException | DaoException e) {
            logger.log(Level.WARN, e);
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
            transactionManager.endTransaction();
        } catch (DaoException | TransactionManagerException e) {
            logger.log(Level.ERROR, e);
        }
        return isDeleted;
    }
    @Test
    public void findByIdAndDishName() {

        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        OrderedDish expected = new OrderedDish(order.getOrderId(), thirdDish.getDishName(), thirdDish.getDishNameRu(), thirdDish.getDishNameEn(), thirdDish.getDescriptionRu(), thirdDish.getDescriptionEn(), thirdDish.getPrice(), DISH_QUANTITY);
        OrderedDish actual = new OrderedDish();
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            actual = orderListDao.findByOrderIdAndDishName(order.getOrderId(), thirdDish.getDishName());

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
                logger.log(Level.ERROR, e);
            }
        }
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void create() {
        OrderedDish actual = null;
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            orderListDao.create(order.getOrderId(), anotherDish.getDishName(), DISH_QUANTITY);
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
                logger.log(Level.ERROR, e);
            }
        }
        try {
                transactionManager.beginTransaction();
                OrderListDao orderListDao = transactionManager.connectOrderListDao();
                actual = orderListDao.findByOrderIdAndDishName(order.getOrderId(), anotherDish.getDishName());
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
        Assert.assertNotNull(actual);
        logger.log(Level.INFO, actual);
    }
    @Test
    public void delete() {
        OrderedDish actual = new OrderedDish();
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            orderListDao.delete(order.getOrderId(), dish.getDishName());
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
                logger.log(Level.ERROR, e);
            }
        }
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            actual = orderListDao.findByOrderIdAndDishName(order.getOrderId(), dish.getDishName());
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
        Assert.assertNull(actual);
    }
}