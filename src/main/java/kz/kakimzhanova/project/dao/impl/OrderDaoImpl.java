package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.dao.OrderDao;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT order_id, login, timestamp FROM orders";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT order_id, login, timestamp FROM orders WHERE order_id=?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (login) VALUES (?)";
    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDERS);
            orders = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setLogin(resultSet.getString("login"));
                order.setTimestamp(resultSet.getTimestamp("timestamp"));
                orders.add(order);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
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
        return orders;
    }

    @Override
    public Order findById(Integer orderId) throws DaoException {
        Order order = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID);
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                order = new Order();
                order.setOrderId(orderId);
                order.setLogin(resultSet.getString("login"));
                order.setTimestamp(resultSet.getTimestamp("timestamp"));
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
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
        return order;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER);
            preparedStatement.setInt(1,id);
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
    public boolean delete(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Order entity) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Order create(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order order = null;
        ResultSet resultSet = null;
        int orderId;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                orderId = resultSet.getInt(1);
                order = findById(orderId);
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
        return order;
    }

    @Override
    public Order update(Order entity) {
        throw new UnsupportedOperationException();
    }
}
