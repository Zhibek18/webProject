package kz.kakimzhanova.delivery.dao.impl;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.dao.DaoParameterHolder;
import kz.kakimzhanova.delivery.dao.DishDao;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDaoImpl implements DishDao {
    private static final String SQL_SELECT_ALL_DISHES = "SELECT dish_name, price FROM menu";
    private static final String SQL_SELECT_DISH_BY_DISHNAME = "SELECT dish_name, price FROM menu WHERE dish_name=?";
    private static final String SQL_DELETE_DISH = "DELETE FROM menu WHERE dish_name=?";
    private static final String SQL_INSERT_DISH = "INSERT INTO menu (dish_name, price) VALUES (?,?)";
    private static final String SQL_UPDATE_DISH = "UPDATE menu SET price=? WHERE dish_name=?";
    @Override
    public List<Dish> findAll() throws DaoException {
        List<Dish> dishes = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_DISHES);
            dishes = new ArrayList<>();
            while (resultSet.next()){
                Dish dish = new Dish();
                dish.setDishName(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME.getName()));
                dish.setPrice(resultSet.getBigDecimal(DaoParameterHolder.PARAM_PRICE.getName()));
                dishes.add(dish);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return dishes;
    }

    @Override
    public Dish findById(String dishName) throws DaoException {
        Dish dish = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISH_BY_DISHNAME);
            preparedStatement.setString(1, dishName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                dish = new Dish();
                dish.setDishName(resultSet.getString(DaoParameterHolder.PARAM_DISH_NAME.getName()));
                dish.setPrice(resultSet.getBigDecimal(DaoParameterHolder.PARAM_PRICE.getName()));
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
        return dish;
    }

    @Override
    public boolean delete(String dishName) throws DaoException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_DISH);
            preparedStatement.setString(1,dishName);
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
    public boolean delete(Dish entity) {
        throw new UnsupportedOperationException("delete not supported in dishDao");
    }

    @Override
    public boolean create(Dish dish) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreated = false;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_DISH);
            preparedStatement.setString(1, dish.getDishName());
            preparedStatement.setBigDecimal(2, dish.getPrice());
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
    public boolean updatePrice(String dishName, BigDecimal newPrice) throws DaoException {
        boolean isUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_DISH);
            preparedStatement.setBigDecimal(1,newPrice);
            preparedStatement.setString(2,dishName);
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
    public Dish update(Dish entity) {
        throw new UnsupportedOperationException();
    }
}
