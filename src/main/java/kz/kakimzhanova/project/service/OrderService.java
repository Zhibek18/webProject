package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.OrderDao;
import kz.kakimzhanova.project.dao.impl.OrderDaoImpl;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService {
    private static Logger logger = LogManager.getLogger();
    private OrderDao orderDao = new OrderDaoImpl();

    public List<Order> findAllOrders(){
        List<Order> orders = null;
        try {
            orders = orderDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return orders;
    }
    public Order newOrder(String login){
        Order order = null;
        try {
            order = orderDao.create(login);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return order;
    }

    public boolean deleteOrder(int orderId){
        boolean isDeleted = false;
        try {
            isDeleted = orderDao.delete(orderId);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return isDeleted;
    }
    public Order findOrderById(int orderId){
        Order order = null;
        try {
            order = orderDao.findById(orderId);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return order;
    }
    public boolean updateTotalCost(int orderId, float totalCost){
        boolean isUpadted = false;
        try{
            isUpadted = orderDao.updateTotalCost(orderId, totalCost);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return isUpadted;
    }
}
