package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.dao.impl.DishDaoImpl;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.DishService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DishServiceImpl implements DishService {
    private static Logger logger = LogManager.getLogger();
    private static final String NAME_VALIDATE_REGEX = "[\\w]{5,20}";
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
    public boolean addDish(Dish dish) throws ServiceException {
        boolean isAdded;
        try{
            if (isValid(dish)) {
                if (dishDao.findById(dish.getDishName())==null) {
                    isAdded = dishDao.create(dish);
                } else {
                    throw new ServiceException("Dish with name " + dish + "already exist");
                }
            } else {
                throw new ServiceException("dish is not valid: " + dish);
            }
        } catch (DaoException e) {
            throw new ServiceException("Couldn't add dish: " + e);
        }
        return isAdded;
    }

    private boolean isValid(Dish dish){
        return isValidName(dish.getDishName()) && isValidPrice(dish.getPrice());
    }

    private boolean isValidName(String name){
        Pattern namePattern = Pattern.compile(NAME_VALIDATE_REGEX);
        Matcher nameMatcher = namePattern.matcher(name);
        return nameMatcher.matches();
    }

    private boolean isValidPrice(BigDecimal price){
        return (price.compareTo(BigDecimal.ZERO) > 0);
    }
    @Override
    public boolean delete(String dishName) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = dishDao.delete(dishName);
        } catch (DaoException e) {
            throw new ServiceException("Couldn't delete dish:" + dishName + " " + e);
        }
        return isDeleted;
    }
    @Override
    public boolean editDish(String dishName, BigDecimal price) throws ServiceException {
        boolean isEdited;
        try {
            if (isValidPrice(price)) {
                isEdited = dishDao.updatePrice(dishName, price);
            } else {
                throw new ServiceException("Not valid price " + price + " for dish " + dishName);
            }
        } catch (DaoException e) {
            throw new ServiceException("Couldn't update dish:" + dishName + " " + e);
        }
        return isEdited;
    }
}
