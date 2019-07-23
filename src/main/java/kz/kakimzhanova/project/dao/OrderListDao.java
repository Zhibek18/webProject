package kz.kakimzhanova.project.dao;

import kz.kakimzhanova.project.entity.OrderList;
import kz.kakimzhanova.project.exception.DaoException;

public interface OrderListDao extends BaseDao<Integer, OrderList>{
boolean updateQuantity(int newQuantity) throws DaoException;
}
