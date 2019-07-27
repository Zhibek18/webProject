package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.impl.OrderDaoImpl;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static Logger logger = LogManager.getLogger();
    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public List<Order> findAllOrders() throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orders;
    }
    @Override
    public Order newOrder(String login) throws ServiceException {
        Order order;
        try {
            order = orderDao.create(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public boolean deleteOrder(int orderId) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = orderDao.delete(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isDeleted;
    }
    @Override
    public Order findOrderById(int orderId) throws ServiceException {
        Order order;
        try {
            order = orderDao.findById(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }
    @Override
    public boolean updateTotalCost(int orderId, float totalCost) throws ServiceException {
        boolean isUpadted;
        try{
            isUpadted = orderDao.updateTotalCost(orderId, totalCost);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isUpadted;
    }
}
