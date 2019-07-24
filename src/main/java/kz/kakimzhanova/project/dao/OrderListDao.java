package kz.kakimzhanova.project.dao;

import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.exception.DaoException;

import java.util.List;

public interface OrderListDao extends BaseDao<Integer, OrderedDish>{
    OrderedDish findByOrderIdAndDishName(int orderId, String dishName) throws DaoException;
    boolean delete(int orderId, String dishName) throws DaoException;
    boolean quantityIncrement(int orderId, String dishName) throws DaoException;
    boolean create(int orderId, String dishName) throws DaoException;
    List<OrderedDish> findByOrderId(int orderId) throws DaoException;
}
