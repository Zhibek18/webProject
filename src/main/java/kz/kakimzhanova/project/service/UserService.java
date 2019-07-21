package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.UserDao;
import kz.kakimzhanova.project.dao.impl.UserDaoImpl;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private static Logger logger = LogManager.getLogger();
    private UserDao userDao = new UserDaoImpl();
    public boolean checkLogin(String login, String password){
        boolean isValid = false;
        try {
            User user = userDao.findById(login);
            if (user != null) {
                isValid = (user.getPassword().equals(password));
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, "Couldn't find login:" + e);
        }
        return isValid;
    }
    public boolean addNewUser(String login, String password){
        if (validateNewUser(login, password)){
            User user = new User(login, password);
            try {
                userDao.create(user);
            } catch (DaoException e) {
                logger.log(Level.WARN, e);
            }
            return true;
        }
        return false;
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
}
