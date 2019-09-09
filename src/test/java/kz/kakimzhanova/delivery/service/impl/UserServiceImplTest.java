package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.dao.impl.UserDaoImpl;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    private final static String NEW_PASSWORD = "test2";
    private static Logger logger = LogManager.getLogger();
    private static User testUser = new User("testUser", "test1", "test", "test", "test", 1, "123456");
    private static User anotherUser = new User("anotherTestUser", "test1", "test", "test", "4/2", 5, "123556");
    private static UserDao userDao;
    private static UserService userService = new UserServiceImpl();
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
        userDao.delete(anotherUser.getLogin());
    }

    @Test
    public void checkLogin() {
        boolean actual = false;
        try {
            actual = userService.checkLogin(testUser.getLogin(), testUser.getPassword());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void isAdmin() {
        boolean actual = true;
        try {
            actual = userService.isAdmin(testUser.getLogin());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertFalse(actual);
    }

    @Test
    public void findById() {
        User expected = testUser;
        User actual = null;
        try {
            actual = userService.findById(testUser.getLogin());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void addNewUser() {
        User expected = anotherUser;
        User actual = null;
        try {
            userService.addNewUser(anotherUser);
            actual = userService.findById(anotherUser.getLogin());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findAllUsers() {
        List<User> users = null;
        try {
            users = userService.findAllUsers();
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        logger.log(Level.DEBUG, users);
        Assert.assertNotNull(users);
        Assert.assertTrue(users.contains(testUser));
    }

    @Test
    public void deleteUser() {
        User deletedUser = null;
        try {
            userService.deleteUser(testUser.getLogin());
            deletedUser = userService.findById(testUser.getLogin());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNull(deletedUser);
    }

    @Test
    public void changePassword() {
        User expected = new User("testUser", "test2", "test", "test", "test", 1, "123456");
        User actual = null;
        try {
            userService.changePassword(testUser.getLogin(), testUser.getPassword(), NEW_PASSWORD);
            actual = userService.findById(testUser.getLogin());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateUser() {
        User expected = new User("testUser", "test1", "test1", "test", "test", 1, "123456");
        User actual = null;
        try {
            userService.updateUser(expected);
            actual = userService.findById(testUser.getLogin());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }
}