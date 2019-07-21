package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.dao.UserDao;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static final String USER_LOGIN ="user";
    private static final String USER_PASSWORD ="pass";
    @Test
    public void findByLogin() {
        User expected = new User(USER_LOGIN, USER_PASSWORD);
        User actual = null;
        UserDao userDao = new UserDaoImpl();
        try {
            actual = userDao.findByLogin(USER_LOGIN);

        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected.toString().trim(), actual.toString().trim());
    }
}