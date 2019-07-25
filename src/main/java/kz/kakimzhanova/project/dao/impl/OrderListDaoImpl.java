package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.dao.OrderListDao;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderListDaoImpl implements OrderListDao {
    private static final String SQL_SELECT_ALL_ORDER_LISTS = "SELECT order_id, dish_name, quantity FROM order_list";
    private static final String SQL_SELECT_ORDER_LIST_BY_ORDER_ID_AND_DISH_NAME = "SELECT order_id, dish_name, quantity FROM order_list WHERE order_id=? AND dish_name=?";
    private static final String SQL_DELETE_ORDER_LIST = "DELETE FROM order_list WHERE order_id =? AND dish_name=?";
    private static final String SQL_INSERT_ORDER_LIST = "INSERT INTO order_list (order_id, dish_name) VALUES (?,?)";
    private static final String SQL_UPDATE_QUANTITY = "UPDATE order_list SET quantity=? WHERE order_id=? AND dish_name=?";
    private static final String SQL_SELECT_BY_ORDER_ID = "SELECT order_list.order_id, order_list.dish_name, menu.price, order_list.quantity FROM order_list INNER JOIN menu ON order_list.dish_name=menu.dish_name AND order_list.order_id=?";
    @Override
    public List<OrderedDish> findAll() throws DaoException {
        List<OrderedDish> orderedDishes = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER_LISTS);
            orderedDishes = new ArrayList<>();
            while(resultSet.next()){
                OrderedDish orderedDish = new OrderedDish();
                orderedDish.setOrderId(resultSet.getInt("order_id"));
                orderedDish.setDishName(resultSet.getString("dish_name"));
                orderedDish.setQuantity(resultSet.getInt("quantity"));
                orderedDishes.add(orderedDish);
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
        return orderedDishes;
    }
    @Override
    public List<OrderedDish> findByOrderId(int orderId) throws DaoException {
        List<OrderedDish> orderedDishes = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ORDER_ID);
            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();
            orderedDishes = new ArrayList<>();
            while(resultSet.next()){
                OrderedDish orderedDish = new OrderedDish();
                orderedDish.setOrderId(resultSet.getInt("order_id"));
                orderedDish.setDishName(resultSet.getString("dish_name"));
                orderedDish.setPrice(resultSet.getFloat("price"));
                orderedDish.setQuantity(resultSet.getInt("quantity"));
                orderedDishes.add(orderedDish);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
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
        return orderedDishes;
    }
    @Override
    public OrderedDish findById(Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderedDish findByOrderIdAndDishName(int orderId, String dishName) throws DaoException {
        OrderedDish orderedDish = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_LIST_BY_ORDER_ID_AND_DISH_NAME);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, dishName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                orderedDish = new OrderedDish();
                orderedDish.setOrderId(resultSet.getInt("order_id"));
                orderedDish.setDishName(resultSet.getString("dish_name"));
                orderedDish.setQuantity(resultSet.getInt("quantity"));
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
        return orderedDish;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(int orderId, String dishName) throws DaoException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_LIST);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, dishName);
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
    public boolean delete(OrderedDish entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(OrderedDish entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(int orderId, String dishName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreated = false;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER_LIST);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, dishName);
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
    public OrderedDish update(OrderedDish entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean quantityIncrement(int orderId, String dishName) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_QUANTITY);
            int oldQuantity = findByOrderIdAndDishName(orderId, dishName).getQuantity();
            int newQuantity = oldQuantity + 1;
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setString(3, dishName);
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
    public boolean updateQuantity(int orderId, String dishName, int quantity) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_QUANTITY);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setString(3, dishName);
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
