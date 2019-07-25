package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.UserDao;
import kz.kakimzhanova.project.dao.impl.UserDaoImpl;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private static Logger logger = LogManager.getLogger();
    private UserDao userDao = new UserDaoImpl();
    public boolean checkLogin(String login, String password){
        boolean isValid = false;
        User user = findById(login);
        if (user != null) {
            isValid = (user.getPassword().equals(password));
        }
        return isValid;
    }
    public boolean isAdmin(String login){
        boolean isAdmin = false;
        User user = findById(login);
        if (user != null) {
            isAdmin = user.isAdmin();
        }
        return isAdmin;
    }
    private User findById(String login){
        User user = null;
        try {
            user = userDao.findById(login);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return user;
    }
    public boolean addNewUser(String login, String password, String firstName, String street, int house, int apartment, String phone){
        boolean added;
        if (validateNewUser(login, password)){
            User user = new User(login, password, firstName, street, house, apartment, phone);
            try {
                added = userDao.create(user);
            } catch (DaoException e) {
                logger.log(Level.WARN, e);
                added = false;
            }
        } else {
            added = false;
        }
        return added;
    }
    private boolean validateNewUser(String login, String password){
        return (validateLogin(login) && validatePassword(password));
    }
    private boolean validateLogin(String login){
        Pattern pattern = Pattern.compile("[\\w\\d]{7,20}");
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }
    private boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("[\\w\\d]{7,20}");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public List<User> findAllUsers(){
        List<User> users = null;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.WARN, "Couldn't find users: " + e);
        }
        return users;
    }
    public boolean deleteUser(String login){
        boolean isDeleted;
        try {
            isDeleted = userDao.delete(login);
        } catch (DaoException e) {
            logger.log(Level.WARN,"Couldn't delete user " + login + ":" + e);
            isDeleted = false;
        }
        return isDeleted;
    }
    public boolean changePassword(String login, String oldPassword, String newPassword){
        boolean isChanged;
        if (checkLogin(login, oldPassword)){
            try {
                isChanged = userDao.updateUserPassword(login, newPassword);
            } catch (DaoException e) {
                logger.log(Level.WARN, "Couldn't change password: "+ e);
                isChanged = false;
            }
        } else {
            isChanged = false;
        }
        return isChanged;
    }
    public boolean changeAddress(String login, String street, int house, int apartment){
        boolean isChanged;
        try{
            isChanged = userDao.updateUserAddress(login, street, house, apartment);
        } catch (DaoException e) {
            logger.log(Level.WARN, "Couldn't change address: "+ e);
            isChanged = false;
        }
        return isChanged;
    }
}
