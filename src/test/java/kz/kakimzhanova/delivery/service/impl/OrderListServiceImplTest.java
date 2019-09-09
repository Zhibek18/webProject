package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.dao.impl.DishDaoImpl;
import kz.kakimzhanova.delivery.dao.impl.UserDaoImpl;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManager;
import kz.kakimzhanova.delivery.transaction.impl.OrderTransactionManagerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class OrderListServiceImplTest {
    private static Logger logger = LogManager.getLogger();
    private static UserDao userDao = new UserDaoImpl();
    private static User testUser = new User("testUser", "test1", "test", "test", "test", 1, "123456");
    private static Order order = null;
    private static DishDao dishDao = new DishDaoImpl();
    private static Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
    private static Dish anotherDish = new Dish("pasta","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(13.50));
    private static Dish thirdDish = new Dish("makaroni","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(16));
    private final static int DISH_QUANTITY = 1;
    private static OrderListService orderListService = new OrderListServiceImpl();
    @BeforeClass
    public static void init(){
        try {
            ConnectionPool.getInstance().initPoolData();
            userDao.create(testUser);
            dishDao.create(dish);
            dishDao.create(anotherDish);
            dishDao.create(thirdDish);
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, e);
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
    }
    @AfterClass
    public static void dispose() {
        try {
            userDao.delete(testUser.getLogin());
            dishDao.delete(dish.getDishName());
            dishDao.delete(anotherDish.getDishName());
            dishDao.delete(thirdDish.getDishName());
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Before
    public void setUp() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            order = orderDao.create(testUser.getLogin());
            orderListDao.create(order.getOrderId(), dish.getDishName(), DISH_QUANTITY);
            orderListDao.create(order.getOrderId(), anotherDish.getDishName(), DISH_QUANTITY);
            transactionManager.commit();
        } catch (DaoException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        } catch (TransactionManagerException e) {
            e.printStackTrace();
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }

    @After
    public void tearDown() {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            orderDao.delete(order.getOrderId());
            transactionManager.commit();
        } catch (DaoException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        } catch (TransactionManagerException e) {
            e.printStackTrace();
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }

    @Test
    public void showOrderList() {
        List<OrderedDish> orderList = null;
        try {
            orderList = orderListService.showOrderList(order.getOrderId());
            logger.log(Level.INFO, orderList);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(orderList);
    }
    @Test
    public void addDish(){
        List<OrderedDish> orderList = null;
        OrderedDish expected = new OrderedDish(order.getOrderId(), thirdDish.getDishName(), thirdDish.getDishNameRu(), thirdDish.getDishNameEn(), thirdDish.getDescriptionRu(), thirdDish.getDescriptionEn(), thirdDish.getPrice(), DISH_QUANTITY);
        try {
            orderListService.addDish(order.getOrderId(), thirdDish.getDishName());
            orderList = orderListService.showOrderList(order.getOrderId());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(orderList);
        Assert.assertTrue(orderList.contains(expected));
    }

    @Test
    public void deleteDish(){
        List<OrderedDish> orderList = null;
        OrderedDish expected = new OrderedDish(order.getOrderId(), dish.getDishName(), dish.getDishNameRu(), dish.getDishNameEn(), dish.getDescriptionRu(), dish.getDescriptionEn(), dish.getPrice(), DISH_QUANTITY);
        try {
            orderListService.deleteDish(order.getOrderId(), dish.getDishName());
            orderList = orderListService.showOrderList(order.getOrderId());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(orderList);
        Assert.assertFalse(orderList.contains(expected));
    }
    @Test
    public void incrementDishQuantity(){
        List<OrderedDish> orderList = null;
        OrderedDish expected = new OrderedDish(order.getOrderId(), dish.getDishName(), dish.getDishNameRu(), dish.getDishNameEn(), dish.getDescriptionRu(), dish.getDescriptionEn(), dish.getPrice(), DISH_QUANTITY + 1);
        try {
            orderListService.changeDishQuantity(order.getOrderId(), dish.getDishName(), DISH_QUANTITY + 1);
            orderList = orderListService.showOrderList(order.getOrderId());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(orderList);
        Assert.assertTrue(orderList.contains(expected));
    }
}