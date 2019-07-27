package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.entity.Entity;
import kz.kakimzhanova.delivery.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao <K,T extends Entity>{
    Logger logger = LogManager.getLogger();

    List<T> findAll() throws DaoException;
    T findById(K id) throws DaoException;
    boolean delete(K id) throws DaoException;
    boolean delete(T entity) throws DaoException;
    boolean create(T entity) throws DaoException;
    T update(T entity);
    default void close (Statement statement){
        try{
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e);
        }
    }
    default void close(Connection connection){
        if (connection != null){
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
    default void close(ResultSet resultSet){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e);
        }
    }
}
