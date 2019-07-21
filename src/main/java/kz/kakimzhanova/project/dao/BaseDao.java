package kz.kakimzhanova.project.dao;

import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.entity.Entity;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao <K,T extends Entity>{
    Logger logger = LogManager.getLogger();

    List<T> findAll() throws DaoException;
    T findById(K id) throws DaoException;
    boolean delete(K id);
    boolean delete(T entity);
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
}
