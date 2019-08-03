package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.service.OrderListService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderListServiceImplTest {
    private static Logger logger = LogManager.getLogger();
    private static OrderListService listService = new OrderListServiceImpl();
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
    public void showOrderList() {
        try {
            List<OrderedDish> orderList = listService.showOrderList(149);
            logger.log(Level.INFO, orderList);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }
}