package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.dao.impl.UserDaoImpl;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private static final String LOGIN_VALIDATE_REGEX = "[\\w]{5,20}";
    private static final String PASSWORD_VALIDATE_REGEX = "[\\w]{5,20}";
    private static final String NAME_VALIDATE_REGEX = "(?U)[\\w]{2,20}";
    private static final String STREET_VALIDATE_REGEX = "(?U)[\\w]{2,20}";
    private static final String PHONE_VALIDATE_REGEX = "[+\\d]{6,12}";
    private static final String PHONE_REPLACE_SYMBOLS_REGEX = "[\\D&&[^+]]";
    private static final String HOUSE_VALIDATE_REGEX = "(?U)[\\w/]{1,10}";
    private static Logger logger = LogManager.getLogger();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean checkLogin(String login, String password) throws ServiceException {
        boolean isValid = false;
        User user = findById(login);
        if (user != null) {
            isValid = (user.getPassword().equals(password));
        }
        return isValid;
    }
    @Override
    public boolean isAdmin(String login) throws ServiceException {
        boolean isAdmin = false;
        User user = findById(login);
        if (user != null) {
            isAdmin = user.isAdmin();
        }
        return isAdmin;
    }
    public User findById(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findById(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }
    @Override
    public boolean addNewUser(User user) throws ServiceException {
        boolean added = false;
        if (validateNewUser(user.getLogin(), user.getPassword(), user.getFirstName(), user.getStreet(), user.getHouse(), user.getApartment(), user.getPhone())){
            try {
                added = userDao.create(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return added;
    }
    private boolean validateNewUser(String login, String password, String firstName, String street, String house, int apartment, String phone){
        return (validateLogin(login) && validatePassword(password) && validateStreet(street) && validatePhone(phone) && (validateFirstName(firstName)) && (validateHouse(house)) && (apartment > 0));
    }
    private boolean validateLogin(String login){
        Pattern loginPattern = Pattern.compile(LOGIN_VALIDATE_REGEX);
        Matcher loginMatcher = loginPattern.matcher(login);
        return loginMatcher.matches();
    }
    private boolean validatePassword(String password){
        Pattern passwordPattern = Pattern.compile(PASSWORD_VALIDATE_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }
    private boolean validateFirstName(String firstName){
        Pattern namePattern = Pattern.compile(NAME_VALIDATE_REGEX);
        Matcher nameMatcher = namePattern.matcher(firstName);
        return nameMatcher.matches();
    }
    private boolean validateStreet(String street){
        Pattern streetPattern = Pattern.compile(STREET_VALIDATE_REGEX);
        Matcher streetMatcher = streetPattern.matcher(street);
        return streetMatcher.matches();
    }
    private boolean validatePhone(String phone){
        Pattern phonePattern = Pattern.compile(PHONE_VALIDATE_REGEX);
        Matcher phoneMatcher = phonePattern.matcher(phone.replaceAll(PHONE_REPLACE_SYMBOLS_REGEX, ""));
        return phoneMatcher.matches();
    }
    private boolean validateHouse(String house){
        Pattern housePattern = Pattern.compile(HOUSE_VALIDATE_REGEX);
        Matcher houseMatcher = housePattern.matcher(house);
        return houseMatcher.matches();
    }
    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(Level.WARN, "Couldn't find users: " + e);
        }
        return users;
    }
    @Override
    public boolean deleteUser(String login) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = userDao.delete(login);
        } catch (DaoException e) {
            throw new ServiceException(Level.WARN,"Couldn't delete user " + login + ":" + e);
        }
        return isDeleted;
    }
    @Override
    public boolean changePassword(String login, String oldPassword, String newPassword) throws ServiceException {
        boolean isChanged = false;
        if (checkLogin(login, oldPassword)) {
            try {
                isChanged = userDao.updateUserPassword(login, newPassword);
            } catch (DaoException e) {
                throw new ServiceException("Couldn't change password: " + e);
            }
        }
        return isChanged;
    }
    @Override
    public User updateUser(User user) throws ServiceException {
        logger.log(Level.DEBUG, "updateUser service : " + user);
        User updatedUser;
        try{
            if (validateUpdateUser(user.getFirstName(), user.getStreet(), user.getHouse(), user.getApartment(), user.getPhone())) {
                updatedUser = userDao.update(user);
                logger.log(Level.DEBUG, "updatedUser service : " + updatedUser);
            } else{
                throw new ServiceException("not valid input: name:" + user.getFirstName() + " street: " + user.getStreet() + " house:" + user.getHouse() + " apartment:" + user.getApartment() + "phone:" + user.getPhone());
            }
        } catch (DaoException e) {
            throw new ServiceException("Couldn't change address: "+ e);
        }
        return updatedUser;
    }
    private boolean validateUpdateUser(String name, String street, String house, int apartment, String phone ){
        return (validateFirstName(name) && validateStreet(street) && validatePhone(phone) && (validateHouse(house)) && (apartment > 0));
    }
}
