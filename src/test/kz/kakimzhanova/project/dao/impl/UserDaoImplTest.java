package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.dao.UserDao;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static final String USER_LOGIN ="user";
    private static final String USER_PASSWORD ="pass";
    private static UserDao userDao;
    @BeforeClass
    public static void init(){
        userDao = new UserDaoImpl();
    }
    @Test
    public void findById() {
        User expected = new User(USER_LOGIN, USER_PASSWORD);
        User actual = null;
        try {
            actual = userDao.findById(USER_LOGIN);

        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected.toString().trim(), actual.toString().trim());
    }

    @Test
    public void create() {
        User user = new User("zhibek125", "zhibek125");
        boolean expected = true;
        boolean actual = false;
        try {
            actual = userDao.create(user);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        boolean expected = true;
        boolean actual = false;
        try {
            actual = userDao.delete("zhibek125");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateUserPassword() {
        boolean expected = true;
        boolean actual = false;
        try {
            actual = userDao.updateUserPassword("olzhas", "pass");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }
}