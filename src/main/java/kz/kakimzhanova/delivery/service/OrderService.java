package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderStatus;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findAllOrders() throws ServiceException;
    boolean deleteOrder(int orderId) throws ServiceException;
    Order findOrderById(int orderId) throws ServiceException;
    boolean updateTotalCost(int orderId, BigDecimal totalCost) throws ServiceException;
    boolean updateOrderStatus(int orderId, OrderStatus status) throws ServiceException;
    List<Order> findOrdersByLogin(String login) throws ServiceException;
    Order createOrder(String login, List<OrderedDish> orderList, BigDecimal totalCost) throws ServiceException;
}
