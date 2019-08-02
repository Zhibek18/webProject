package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.DaoParameterHolder;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderListDaoImpl implements OrderListDao {
    private static final String SQL_SELECT_ALL_ORDER_LISTS = "SELECT order_list.order_id, order_list.dish_name, order_list.quantity, menu.dish_name_ru, menu.dish_name_en FROM order_list INNER JOIN menu ON order_list.dish_name=menu.dish_name";
    private static final String SQL_SELECT_ORDER_LIST_BY_ORDER_ID_AND_DISH_NAME = "SELECT order_list.order_id, order_list.dish_name, order_list.quantity, menu.dish_name_ru, menu.dish_name_en FROM order_list INNER JOIN menu ON order_list.dish_name=? AND menu.dish_name=order_list.dish_name WHERE order_id=?";
    private static final String SQL_DELETE_ORDER_LIST = "DELETE FROM order_list WHERE order_id =? AND dish_name=?";
    private static final String SQL_INSERT_ORDER_LIST = "INSERT INTO order_list (order_id, dish_name, quantity) VALUES (?,?,?)";
    private static final String SQL_UPDATE_QUANTITY = "UPDATE order_list SET quantity=? WHERE order_id=? AND dish_name=?";
    private static final String SQL_SELECT_BY_ORDER_ID = "SELECT order_list.order_id, order_list.dish_name, menu.price, menu.dish_name_ru, menu.dish_name_en, order_list.quantity FROM order_list INNER JOIN menu ON order_list.dish_name=menu.dish_name AND order_list.order_id=?";
    private Connection connection;

    public OrderListDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<OrderedDish> findAll() throws DaoException {
        List<OrderedDish> orderedDishes;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER_LISTS);
            orderedDishes = new ArrayList<>();
            while(resultSet.next()){
                OrderedDish orderedDish = new OrderedDish();
                orderedDish.setOrderId(resultSet.getInt(DaoParameterHolder.PARAM_ORDER_ID.getName()));
                orderedDish.setDishName(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME.getName()));
                orderedDish.setDishNameRu(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME_RU.getName()));
                orderedDish.setDishNameEn(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME_EN.getName()));
                orderedDish.setQuantity(resultSet.getInt(DaoParameterHolder.PARAM_QUANTITY.getName()));
                orderedDishes.add(orderedDish);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(statement);
        }
        return orderedDishes;
    }
    @Override
    public List<OrderedDish> findByOrderId(int orderId) throws DaoException {
        List<OrderedDish> orderedDishes;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ORDER_ID);
            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();
            orderedDishes = new ArrayList<>();
            while(resultSet.next()){
                OrderedDish orderedDish = new OrderedDish();
                orderedDish.setOrderId(resultSet.getInt(DaoParameterHolder.PARAM_ORDER_ID.getName()));
                orderedDish.setDishName(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME.getName()));
                orderedDish.setPrice(resultSet.getBigDecimal(DaoParameterHolder.PARAM_PRICE.getName()));
                orderedDish.setDishNameRu(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME_RU.getName()));
                orderedDish.setDishNameEn(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME_EN.getName()));
                orderedDish.setQuantity(resultSet.getInt(DaoParameterHolder.PARAM_QUANTITY.getName()));
                orderedDishes.add(orderedDish);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_LIST_BY_ORDER_ID_AND_DISH_NAME);
            preparedStatement.setString(1, dishName);
            preparedStatement.setInt(2, orderId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                orderedDish = new OrderedDish();
                orderedDish.setOrderId(resultSet.getInt(DaoParameterHolder.PARAM_ORDER_ID.getName()));
                orderedDish.setDishName(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME.getName()));
                orderedDish.setDishNameRu(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME_RU.getName()));
                orderedDish.setDishNameEn(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME_EN.getName()));
                orderedDish.setQuantity(resultSet.getInt(DaoParameterHolder.PARAM_QUANTITY.getName()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return orderedDish;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(int orderId, String dishName) throws DaoException {
        boolean isDeleted;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_LIST);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, dishName);
            preparedStatement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
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
    public boolean create(int orderId, String dishName, int quantity) throws DaoException {
        PreparedStatement preparedStatement = null;
        boolean isCreated;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER_LIST);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, dishName);
            preparedStatement.setInt(3,quantity);
            preparedStatement.executeUpdate();
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
        }
        return isCreated;
    }

    @Override
    public OrderedDish update(OrderedDish entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean quantityIncrement(int orderId, String dishName) throws DaoException {
        boolean isUpdated;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE_QUANTITY);
            int oldQuantity = findByOrderIdAndDishName(orderId, dishName).getQuantity();
            int newQuantity = oldQuantity + 1;
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setString(3, dishName);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(preparedStatement);
        }
        return isUpdated;
    }
    @Override
    public boolean updateQuantity(int orderId, String dishName, int quantity) throws DaoException {
        boolean isUpdated;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE_QUANTITY);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setString(3, dishName);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(preparedStatement);
        }
        return isUpdated;
    }

}
