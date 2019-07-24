package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.OrderDao;
import kz.kakimzhanova.project.dao.impl.OrderDaoImpl;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderService {
    private static Logger logger = LogManager.getLogger();
    private OrderDao orderDao = new OrderDaoImpl();

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
}
