package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.DishDao;
import kz.kakimzhanova.project.dao.impl.DishDaoImpl;
import kz.kakimzhanova.project.entity.Dish;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DishService {
    private static Logger logger = LogManager.getLogger();
    private DishDao dishDao = new DishDaoImpl();

    public List<Dish> findAllDishes(){
        List<Dish> dishes = null;
        try {
            dishes = dishDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.WARN, "Couldn't find all dishes: " + e);
        }
        return dishes;
    }
}
