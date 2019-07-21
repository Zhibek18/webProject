package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.UserDao;
import kz.kakimzhanova.project.dao.impl.UserDaoImpl;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    private static Logger logger = LogManager.getLogger();
    public boolean checkLogin(String login, String password){
        boolean isValid = false;
        UserDao userDao = new UserDaoImpl();
        try {
            User user = userDao.findByLogin(login);
            isValid = (user.getPassword().equals(password));
        } catch (DaoException e) {
            logger.log(Level.WARN, "Couldn't find login:" + e);
        }
        return isValid;
    }
}
