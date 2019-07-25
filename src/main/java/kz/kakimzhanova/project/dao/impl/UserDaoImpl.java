package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.dao.UserDao;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String SQL_SELECT_ALL_USERS = "SELECT login FROM users";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT login, password, is_admin, first_name, street, house, apartment, phone FROM users WHERE login=?";
    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, first_name, street, house, apartment, phone ) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE login=?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE login=?";
    private static final String SQL_UPDATE_USER_ADDRESS = "UPDATE users SET street=?, house=?, apartment=? WHERE login=?";

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_STREET = "street";
    private static final String PARAM_HOUSE = "house";
    private static final String PARAM_APARTMENT = "apartment";
    private static final String PARAM_PHONE = "phone";
    private static final String PARAM_IS_ADMIIN = "is_admin";
    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_NAME_LOGIN));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } finally {
            close(statement);
            close(connection);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARN, e);
            }
        }
        return users;
    }

    @Override
    public boolean delete(String login) throws DaoException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setString(1,login);
            preparedStatement.executeUpdate();
            isDeleted = true;
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
        close(preparedStatement);
        close(connection);
        }
        return isDeleted;
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreated = false;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4,user.getStreet());
            preparedStatement.setInt(5, user.getHouse());
            preparedStatement.setInt(6, user.getApartment());
            preparedStatement.setString(7, user.getPhone());
            preparedStatement.executeUpdate();
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return isCreated;
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException();
    }


    @Override
    public User findById(String login) throws DaoException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.setLogin(login);
                user.setPassword(resultSet.getString(PARAM_NAME_PASSWORD));
                user.setFirstName(resultSet.getString(PARAM_FIRST_NAME));
                user.setStreet(resultSet.getString(PARAM_STREET));
                user.setHouse(resultSet.getInt(PARAM_HOUSE));
                user.setApartment(resultSet.getInt(PARAM_APARTMENT));
                user.setPhone(resultSet.getString(PARAM_PHONE));
                user.setAdmin(resultSet.getBoolean(PARAM_IS_ADMIIN));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } finally {
            close(preparedStatement);
            close(connection);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARN, e);
            }
        }
        return user;
    }

    @Override
    public boolean updateUserPassword(String login, String newPassword) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD);
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,login);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(preparedStatement);
            close(connection);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserAddress(String login, String street, int house, int apartment) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_ADDRESS);
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, house);
            preparedStatement.setInt(3, apartment);
            preparedStatement.setString(4, login);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(preparedStatement);
            close(connection);
        }
        return isUpdated;
    }
}
