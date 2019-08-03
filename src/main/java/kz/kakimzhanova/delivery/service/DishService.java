package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface DishService {
    List<Dish> findAllDishes() throws ServiceException;
    boolean addDish(Dish dish) throws ServiceException;
    boolean delete(String dishName) throws ServiceException;
    boolean editDish(Dish dish) throws ServiceException;
}
