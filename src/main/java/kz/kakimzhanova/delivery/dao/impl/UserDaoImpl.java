package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.DaoParameterHolder;
import kz.kakimzhanova.delivery.dao.UserDao;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.DaoException;
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
                user.setLogin(resultSet.getString(DaoParameterHolder.PARAM_LOGIN.getName()));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
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
                user.setPassword(resultSet.getString(DaoParameterHolder.PARAM_PASSWORD.getName()));
                user.setFirstName(resultSet.getString(DaoParameterHolder.PARAM_FIRST_NAME.getName()));
                user.setStreet(resultSet.getString(DaoParameterHolder.PARAM_STREET.getName()));
                user.setHouse(resultSet.getInt(DaoParameterHolder.PARAM_HOUSE.getName()));
                user.setApartment(resultSet.getInt(DaoParameterHolder.PARAM_APARTMENT.getName()));
                user.setPhone(resultSet.getString(DaoParameterHolder.PARAM_PHONE.getName()));
                user.setAdmin(resultSet.getBoolean(DaoParameterHolder.PARAM_IS_ADMIN.getName()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);

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
