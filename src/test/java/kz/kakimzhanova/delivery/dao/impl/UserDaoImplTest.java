package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

public class UserDaoImplTest {
    private static Logger logger = LogManager.getLogger();
    private static User testUser = new User("testUser", "test1", "test", "test", "test", 1, "123456");
    private static User anotherUser = new User("anotherTestUser", "test1", "test", "test", "4/2", 5, "123556");
    private static UserDao userDao;
    @BeforeClass
    public static void init(){
        userDao = new UserDaoImpl();
        try {
            ConnectionPool.getInstance().initPoolData();

        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, e);
        }
    }

    @AfterClass
    public static void dispose() {
        try {

            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }

    @Before
    public void setUp() throws DaoException {
        userDao = new UserDaoImpl();
        userDao.create(testUser);
    }

    @After
    public void tearDown() throws Exception {
        userDao.delete(testUser.getLogin());
    }

    @Test
    public void findById() {
        User expected = testUser;
        User actual = null;
        try {
            actual = userDao.findById(testUser.getLogin());

        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        if (actual != null) {
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void create() {
        User expected = anotherUser;
        User actual = null;
        try {
            userDao.create(anotherUser);
            actual = userDao.findById(anotherUser.getLogin());
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        if (actual != null) {
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void delete() {
        User actual = testUser;
        try {
            userDao.delete(testUser.getLogin());
            actual = userDao.findById(testUser.getLogin());
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(actual);
    }

    @Test
    public void updateUserPassword() {
        boolean expected = true;
        boolean actual = false;
        try {
            actual = userDao.updateUserPassword(testUser.getLogin(), "newPass");
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(expected, actual);
    }


}