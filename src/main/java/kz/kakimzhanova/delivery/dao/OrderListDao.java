package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.DaoException;

import java.util.List;

public interface OrderListDao extends BaseDao<Integer, OrderedDish>{
    OrderedDish findByOrderIdAndDishName(int orderId, String dishName) throws DaoException;
    boolean delete(int orderId, String dishName) throws DaoException;
    boolean quantityIncrement(int orderId, String dishName) throws DaoException;
    boolean create(int orderId, String dishName, int quantity) throws DaoException;
    List<OrderedDish> findByOrderId(int orderId) throws DaoException;
    boolean updateQuantity(int orderId, String dishName, int quantity) throws DaoException;
}
