package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.ServiceException;

import java.util.List;

public interface DishService {
    List<Dish> findAllDishes() throws ServiceException;
}
