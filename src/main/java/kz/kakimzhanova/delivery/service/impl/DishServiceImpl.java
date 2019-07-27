package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.dao.impl.DishDaoImpl;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DishServiceImpl implements DishService {
    private static Logger logger = LogManager.getLogger();
    private DishDao dishDao = new DishDaoImpl();

    @Override
    public List<Dish> findAllDishes() throws ServiceException {
        List<Dish> dishes;
        try {
            dishes = dishDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Couldn't find all dishes: " + e);
        }
        return dishes;
    }
}
