package kz.kakimzhanova.project.dao;

import kz.kakimzhanova.project.entity.Dish;
import kz.kakimzhanova.project.exception.DaoException;

import java.math.BigDecimal;

public interface DishDao extends BaseDao<String, Dish>{
    boolean updatePrice(String dishName, BigDecimal newPrice) throws DaoException;
}
