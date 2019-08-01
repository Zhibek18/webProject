package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static final String USER_LOGIN ="user";
    private static final String USER_PASSWORD ="pass";
    private static final String USER_FIRST_NAME = "User";
    private static final String USER_STREET = "Aaaa";
    private static final String USER_HOUSE = "1";
    private static final int USER_APPARTMENT = 3;
    private static final String USER_PHONE = "123333";
    private static UserDao userDao;
    @BeforeClass
    public static void init(){
        userDao = new UserDaoImpl();
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }
    @Test
    public void findById() {
        User expected = new User(USER_LOGIN, USER_PASSWORD, USER_FIRST_NAME, USER_STREET, USER_HOUSE, USER_APPARTMENT, USER_PHONE);
        User actual = null;
        try {
            actual = userDao.findById(USER_LOGIN);

        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        if (actual != null) {
            Assert.assertEquals(expected.toString().trim(), actual.toString().trim());
        }
    }

    @Test
    public void create() {
        User user = new User("zhibek125", "zhibek125", "Zhibek", "B1", "4/2", 5, "123556");
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
            actual = userDao.updateUserPassword("user", "p");
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void dispose() {
        try {
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }
}