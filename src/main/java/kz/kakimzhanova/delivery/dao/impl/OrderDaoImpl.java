package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.DaoParameterHolder;
import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final String SQL_SELECT_ALL_ORDERS = "select orders.order_id, orders.timestamp, orders.total_cost, orders.status, users.first_name, users.street, users.house, users.apartment, users.phone from orders inner join users on (orders.login = users.login)";
    private static final String SQL_SELECT_ORDER_BY_ID = "select orders.order_id, orders.timestamp, orders.total_cost, orders.status, users.first_name, users.street, users.house, users.apartment, users.phone from orders inner join users on (orders.login = users.login) and orders.order_id=?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (login) VALUES (?)";
    private static final String SQL_UPDATE_TOTAL_COST = "UPDATE orders SET total_cost=? WHERE order_id=?";
    private static final String SQL_UPDATE_STATUS = "UPDATE orders SET status=? WHERE order_id=?";
    private static final String SQL_SELECT_ORDERS_BY_LOGIN = "select orders.order_id, orders.timestamp, orders.total_cost, orders.status, users.first_name, users.street, users.house, users.apartment, users.phone from orders inner join users on (orders.login = users.login and orders.login=?)";
    private OrderListDao orderListDao = new OrderListDaoImpl();
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
                int orderId = resultSet.getInt(DaoParameterHolder.PARAM_ORDER_ID.getName());
                order.setOrderId(orderId);
                order.setTimestamp(resultSet.getTimestamp(DaoParameterHolder.PARAM_TIMESTAMP.getName()));
                order.setFirstName(resultSet.getString(DaoParameterHolder.PARAM_FIRST_NAME.getName()));
                order.setStreet(resultSet.getString(DaoParameterHolder.PARAM_STREET.getName()));
                order.setHouse(resultSet.getString(DaoParameterHolder.PARAM_HOUSE.getName()));
                order.setApartment(resultSet.getInt(DaoParameterHolder.PARAM_APARTMENT.getName()));
                order.setPhone(resultSet.getString(DaoParameterHolder.PARAM_PHONE.getName()));
                order.setStatus(resultSet.getInt(DaoParameterHolder.PARAM_STATUS.getName()));
                order.setOrderList(orderListDao.findByOrderId(orderId));
                order.setTotalCost(resultSet.getBigDecimal(DaoParameterHolder.PARAM_TOTAL_COST.getName()));
                orders.add(order);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
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
                order.setTimestamp(resultSet.getTimestamp(DaoParameterHolder.PARAM_TIMESTAMP.getName()));
                order.setFirstName(resultSet.getString(DaoParameterHolder.PARAM_FIRST_NAME.getName()));
                order.setStreet(resultSet.getString(DaoParameterHolder.PARAM_STREET.getName()));
                order.setHouse(resultSet.getString(DaoParameterHolder.PARAM_HOUSE.getName()));
                order.setApartment(resultSet.getInt(DaoParameterHolder.PARAM_APARTMENT.getName()));
                order.setPhone(resultSet.getString(DaoParameterHolder.PARAM_PHONE.getName()));
                order.setStatus(resultSet.getInt(DaoParameterHolder.PARAM_STATUS.getName()));
                order.setOrderList(orderListDao.findByOrderId(orderId));
                order.setTotalCost(resultSet.getBigDecimal(DaoParameterHolder.PARAM_TOTAL_COST.getName()));
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return order;
    }

    @Override
    public boolean delete(Integer orderId) throws DaoException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER);
            preparedStatement.setInt(1,orderId);
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
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return order;
    }

    @Override
    public Order update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateTotalCost(int orderId, BigDecimal totalCost) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_TOTAL_COST);
            preparedStatement.setBigDecimal(1, totalCost);
            preparedStatement.setInt(2, orderId);
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
    public boolean updateStatus(int orderId, int status) throws DaoException{
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, orderId);
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
    public List<Order> findByLogin(String login) throws DaoException {
        List<Order> orders = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            orders = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order();
                int orderId = resultSet.getInt(DaoParameterHolder.PARAM_ORDER_ID.getName());
                order.setOrderId(orderId);
                order.setTimestamp(resultSet.getTimestamp(DaoParameterHolder.PARAM_TIMESTAMP.getName()));
                order.setFirstName(resultSet.getString(DaoParameterHolder.PARAM_FIRST_NAME.getName()));
                order.setStreet(resultSet.getString(DaoParameterHolder.PARAM_STREET.getName()));
                order.setHouse(resultSet.getString(DaoParameterHolder.PARAM_HOUSE.getName()));
                order.setApartment(resultSet.getInt(DaoParameterHolder.PARAM_APARTMENT.getName()));
                order.setPhone(resultSet.getString(DaoParameterHolder.PARAM_PHONE.getName()));
                order.setStatus(resultSet.getInt(DaoParameterHolder.PARAM_STATUS.getName()));
                order.setOrderList(orderListDao.findByOrderId(orderId));
                order.setTotalCost(resultSet.getBigDecimal(DaoParameterHolder.PARAM_TOTAL_COST.getName()));
                orders.add(order);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return orders;
    }
}
