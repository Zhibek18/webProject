package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.DaoException;

public interface OrderDao extends BaseDao<Integer, Order>{
    Order create(String login) throws DaoException;
    boolean updateTotalCost(int orderId, float totalCost) throws DaoException;
}
