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
    private static final String SQL_SELECT_ALL_USERS = "SELECT login, password FROM users";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT login, password FROM users WHERE login=?";
    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password) VALUES (?,?)";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE login=?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE login=?";
    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setString(1,login);
            preparedStatement.executeUpdate();
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
        close(preparedStatement);
        close(connection);
        }
        return true;
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return true;
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
                user = new User(login, resultSet.getString("password"));
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
    public boolean updateUserPassword(String login, String password) throws DaoException {
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD);
            preparedStatement.setString(1,password);
            preparedStatement.setString(2,login);
            preparedStatement.executeUpdate();
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }
}
