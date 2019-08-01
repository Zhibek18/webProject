package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.DaoException;

import java.math.BigDecimal;

public interface DishDao extends BaseDao<String, Dish>{
    boolean update(String dishName,String dishNameRu, String dishNameEn, String descriptionRu, String descriptionEn, BigDecimal newPrice) throws DaoException;
}
