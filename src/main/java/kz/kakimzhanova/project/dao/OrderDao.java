package kz.kakimzhanova.project.dao;

import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.exception.DaoException;

public interface OrderDao extends BaseDao<Integer, Order>{
    Order create(String login) throws DaoException;
}
