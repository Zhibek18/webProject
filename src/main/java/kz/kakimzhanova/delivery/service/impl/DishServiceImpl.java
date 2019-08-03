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
    private static final String RU_NAME_VALIDATE_REGEX = "[А-Я][А-Яа-я -()]{1,19}";
    private static final String EN_NAME_VALIDATE_REGEX = "[A-Z][A-Za-z -()]{1,19}";
    private static final String RU_DESCRIPTION_VALIDATE_REGEX = "[А-Яа-я][А-Яа-я -]{1,49}";
    private static final String EN_DESCRIPTION_VALIDATE_REGEX = "[A-Za-z][A-Za-z -]{1,49}";
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
        try {
            if (isValid(dish)) {
                if (dishDao.findById(dish.getDishName()) == null) {
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

    private boolean isValid(Dish dish) {
        return isValidName(dish.getDishName()) && isValidPrice(dish.getPrice());
    }

    private boolean isValidName(String name) {
        Pattern namePattern = Pattern.compile(NAME_VALIDATE_REGEX);
        Matcher nameMatcher = namePattern.matcher(name);
        return nameMatcher.matches();
    }

    private boolean isValidPrice(BigDecimal price) {
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
    public boolean editDish(Dish dish) throws ServiceException {
        boolean isEdited;
        try {
            if (isValidUpdate(dish.getDishNameRu(), dish.getDishNameEn(), dish.getDescriptionRu(), dish.getDescriptionEn(), dish.getPrice())) {
                Dish updatedDish = dishDao.update(dish);
                isEdited = (updatedDish != null);
            } else {
                throw new ServiceException("Not valid dish " + dish.getDishName() + ":" + "\ndishNameRu="+dish.getDishNameRu() + "\ndishNameEn="+dish.getDishNameEn()+ "\ndescriptionRu="+dish.getDescriptionRu() + "\ndescriptionEn="+dish.getDescriptionEn() +"\nprice="+dish.getPrice());
            }
        } catch (DaoException e) {
            throw new ServiceException("Couldn't update dish:" + dish.getDishName() + " " + e);
        }
        return isEdited;
    }

    private boolean isValidUpdate(String dishNameRu, String dishNameEn, String descriptionRu, String descriptionEn, BigDecimal price) {
        return ((isValidNameRu(dishNameRu)) && (isValidNameEn(dishNameEn)) && (isValidPrice(price)) && (isValidDescriptionRu(descriptionRu)) && (isValidDescriptionEn(descriptionEn)));
    }

    private boolean isValidNameRu(String nameRu) {
        Pattern ruNamePattern = Pattern.compile(RU_NAME_VALIDATE_REGEX);
        Matcher ruNameMatcher = ruNamePattern.matcher(nameRu);
        return ruNameMatcher.matches();
    }

    private boolean isValidNameEn(String nameEn) {
        Pattern enNamePattern = Pattern.compile(EN_NAME_VALIDATE_REGEX);
        Matcher enNameMatcher = enNamePattern.matcher(nameEn);
        return enNameMatcher.matches();
    }
    private boolean isValidDescriptionRu(String descriptionRu){
        Pattern ruDescriptionPattern = Pattern.compile(RU_DESCRIPTION_VALIDATE_REGEX);
        Matcher ruDescriptionMatcher = ruDescriptionPattern.matcher(descriptionRu);
        return ruDescriptionMatcher.matches();
    }
    private boolean isValidDescriptionEn(String descriptionEn){
        Pattern enDescriptionPattern = Pattern.compile(EN_DESCRIPTION_VALIDATE_REGEX);
        Matcher enDescriptionMatcher = enDescriptionPattern.matcher(descriptionEn);
        return enDescriptionMatcher.matches();
    }
}
