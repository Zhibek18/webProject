package kz.kakimzhanova.project.dao.impl;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.dao.DishDao;
import kz.kakimzhanova.project.entity.Dish;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDaoImpl implements DishDao {
    private static final String SQL_SELECT_ALL_DISHES = "SELECT * FROM menu";
    private static final String SQL_SELECT_DISH_BY_DISHNAME = "SELECT dishname, price FROM menu WHERE dishname=?";

    @Override
    public List<Dish> findAll() throws DaoException {
        List<Dish> dishes = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(SQL_SELECT_ALL_DISHES);
            while (resultSet.next()){
                Dish dish = new Dish();
                dish.setDishname(resultSet.getString("dishname"));
                dish.setPrice(resultSet.getBigDecimal("price"));
                dishes.add(dish);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
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
        return dishes;
    }

    @Override
    public Dish findById(String dishname) throws DaoException {
        Dish dish = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISH_BY_DISHNAME);
            preparedStatement.setString(1, dishname);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                dish = new Dish();
                dish.setDishname(resultSet.getString("dishname"));
                dish.setPrice(BigDecimal.valueOf(resultSet.getFloat("price")));
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
        return dish;
    }

    @Override
    public boolean delete(String id) throws DaoException {
        throw new UnsupportedOperationException("delete not supported in dishDao");
    }

    @Override
    public boolean delete(Dish entity) {
        throw new UnsupportedOperationException("delete not supported in dishDao");
    }

    @Override
    public boolean create(Dish entity) throws DaoException {
        throw new UnsupportedOperationException("create not supported in dishDao");
    }

    @Override
    public Dish update(Dish entity) {
        throw new UnsupportedOperationException("update not supported in dishDao");
    }
}
