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
    private User findById(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findById(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }
    @Override
    public boolean addNewUser(String login, String password, String firstName, String street, int house, int apartment, String phone) throws ServiceException {
        boolean added = false;
        if (validateNewUser(login, password)){
            User user = new User(login, password, firstName, street, house, apartment, phone);
            try {
                added = userDao.create(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return added;
    }
    private boolean validateNewUser(String login, String password){
        return (validateLogin(login) && validatePassword(password));
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
    public boolean changeAddress(String login, String street, int house, int apartment) throws ServiceException {
        boolean isChanged;
        try{
            isChanged = userDao.updateUserAddress(login, street, house, apartment);
        } catch (DaoException e) {
            throw new ServiceException("Couldn't change address: "+ e);
        }
        return isChanged;
    }
}
