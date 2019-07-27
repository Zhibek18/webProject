package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findAllOrders() throws ServiceException;
    Order newOrder(String login) throws ServiceException;
    boolean deleteOrder(int orderId) throws ServiceException;
    Order findOrderById(int orderId) throws ServiceException;
    boolean updateTotalCost(int orderId, BigDecimal totalCost) throws ServiceException;
}
