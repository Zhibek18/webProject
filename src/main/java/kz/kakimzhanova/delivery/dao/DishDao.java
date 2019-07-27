package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.DaoException;

import java.math.BigDecimal;

public interface DishDao extends BaseDao<String, Dish>{
    boolean updatePrice(String dishName, BigDecimal newPrice) throws DaoException;
}
