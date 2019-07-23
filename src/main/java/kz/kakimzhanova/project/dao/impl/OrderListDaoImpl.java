package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.dao.OrderListDao;
import kz.kakimzhanova.project.entity.OrderList;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderListDaoImpl implements OrderListDao {
    private static final String SQL_SELECT_ALL_ORDER_LISTS = "SELECT order_list_id, order_id, dish_name, quantity FROM order_lists";
    private static final String SQL_SELECT_ORDER_LIST_BY_ID = "SELECT order_list_id, order_id, dish_name, quantity FROM order_lists WHERE order_list_id=?";
    private static final String SQL_DELETE_ORDER_LIST = "DELETE FROM order_lists WHERE order_list_id=?";
    private static final String SQL_INSERT_ORDER_LIST = "INSERT INTO order_lists (order_id, dish_name, quantity) VALUES (?,?,?)";
    private static final String SQL_UPDATE_QUANTITY = "UPDATE order_lists SET quantity=?";
    @Override
    public List<OrderList> findAll() throws DaoException {
        List<OrderList> orderLists = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER_LISTS);
            orderLists = new ArrayList<>();
            while(resultSet.next()){
                OrderList orderList = new OrderList();
                orderList.setOrderListId(resultSet.getInt("order_list_id"));
                orderList.setOrderId(resultSet.getInt("order_id"));
                orderList.setDishName(resultSet.getString("dish_name"));
                orderList.setQuantity(resultSet.getInt("quantity"));
                orderLists.add(orderList);
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
        return orderLists;
    }

    @Override
    public OrderList findById(Integer id) throws DaoException {
        OrderList orderList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_LIST_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                orderList = new OrderList();
                orderList.setOrderListId(resultSet.getInt("order_list_id"));
                orderList.setOrderId(resultSet.getInt("order_id"));
                orderList.setDishName(resultSet.getString("dish_name"));
                orderList.setQuantity(resultSet.getInt("quantity"));
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
        return orderList;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_LIST);
            preparedStatement.setInt(1, id);
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
    public boolean delete(OrderList entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(OrderList orderList) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreated = false;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER_LIST);
            preparedStatement.setInt(1, orderList.getOrderId());
            preparedStatement.setString(2, orderList.getDishName());
            preparedStatement.setInt(3,orderList.getQuantity());
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
    public OrderList update(OrderList entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateQuantity(int newQuantity) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_QUANTITY);
            preparedStatement.setInt(1, newQuantity);
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
