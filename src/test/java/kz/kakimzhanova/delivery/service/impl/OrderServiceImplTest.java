package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest {
    private static Logger logger = LogManager.getLogger();
    private static OrderService orderService = new OrderServiceImpl();
    @BeforeClass
    public static void init(){
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
    public void createOrder() {
        try {
            orderService.createOrder("user", null, BigDecimal.valueOf(200));
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Test
    public void deleteOrder() {
        try {
            orderService.deleteOrder(153);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Test
    public void findOrderById() {
        try {
            Order order = orderService.findOrderById(149);
            logger.log(Level.INFO, order);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Test
    public void updateOrderStatus() {
        try {
            orderService.updateOrderStatus(149, 2);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Test
    public void findOrdersByLogin() {
        try {
            List<Order> orders = orderService.findOrdersByLogin("zhibek");
            logger.log(Level.INFO, orders);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }


}