package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderStatus;
import kz.kakimzhanova.delivery.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao extends BaseDao<Integer, Order>{
    Order create(String login) throws DaoException;
    boolean updateTotalCost(int orderId, BigDecimal totalCost) throws DaoException;
    boolean updateStatus(int orderId, OrderStatus status) throws DaoException;
    List<Order> findByLogin(String login) throws DaoException;
}
