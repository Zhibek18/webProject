package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.dao.impl.DishDaoImpl;
import kz.kakimzhanova.delivery.dao.impl.UserDaoImpl;
import kz.kakimzhanova.delivery.entity.*;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManager;
import kz.kakimzhanova.delivery.transaction.impl.OrderTransactionManagerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest {
    private static Logger logger = LogManager.getLogger();
    private static UserDao userDao = new UserDaoImpl();
    private static User testUser = new User("testUser", "test1", "test", "test", "test", 1, "123456");
    private static Order order = null;
    private static DishDao dishDao = new DishDaoImpl();
    private static Dish dish = new Dish("spaghetti","Спагетти", "Spaghetti", "Вареные спагетти","Boiled spaghetti", BigDecimal.valueOf(13.50));
    private static Dish anotherDish = new Dish("pasta","Паста", "Pasta", "Паста","Pasta", BigDecimal.valueOf(13.50));
    private final static int DISH_QUANTITY = 1;
    private final static OrderStatus NEW_ORDER_STATUS = OrderStatus.CONFIRMED;
    private static OrderService orderService = new OrderServiceImpl();
    @BeforeClass
    public static void init(){
        try {
            ConnectionPool.getInstance().initPoolData();
            userDao.create(testUser);
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
            userDao.delete(testUser.getLogin());
            dishDao.delete(dish.getDishName());
            dishDao.delete(anotherDish.getDishName());
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
            order = orderService.findOrderById(order.getOrderId());
            logger.log(Level.DEBUG, order);
        } catch (DaoException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, "Rollback failed: " + ex);
            }
            logger.log(Level.ERROR, e);
        } catch (TransactionManagerException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
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
    public void createOrder() {
        Order newOrder = null;
        try {
            newOrder = orderService.createOrder(testUser.getLogin(), null, BigDecimal.ZERO);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(newOrder);
    }

    @Test
    public void deleteOrder() {
        Order deletedOrder = order;
        try {
            orderService.deleteOrder(order.getOrderId());
            deletedOrder = orderService.findOrderById(order.getOrderId());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(deletedOrder);
    }

    @Test
    public void findOrderById() {
        Order foundOrder = null;
        try {
            foundOrder = orderService.findOrderById(order.getOrderId());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(order, foundOrder);
    }

    @Test
    public void updateOrderStatus() {
        Order foundOrder = null;
        try {
            orderService.updateOrderStatus(order.getOrderId(), NEW_ORDER_STATUS);
            foundOrder = orderService.findOrderById(order.getOrderId());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(foundOrder);
        Assert.assertEquals(foundOrder.getStatus(), NEW_ORDER_STATUS);
    }

    @Test
    public void findOrdersByLogin() {
        List<Order> orders = null;
        try {
            orders = orderService.findOrdersByLogin(testUser.getLogin());
            logger.log(Level.INFO, orders);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(orders);
    }
}