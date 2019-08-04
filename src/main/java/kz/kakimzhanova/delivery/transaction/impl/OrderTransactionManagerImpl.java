package kz.kakimzhanova.delivery.transaction.impl;

import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.dao.impl.OrderDaoImpl;
import kz.kakimzhanova.delivery.dao.impl.OrderListDaoImpl;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides OrderService and OrderList service with access to creating daoObject with Connection
 */
public class OrderTransactionManagerImpl implements OrderTransactionManager {
    private Connection connection;

    /**
     * takes connection from ConnectionPool
     * @throws TransactionManagerException
     */
    public void beginTransaction() throws TransactionManagerException {
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not start transactions " + e);
        }
    }

    /**
     * @return OrderDao
     */
    public OrderDao connectOrderDao(){
        return new OrderDaoImpl(connection);
    }

    /**
     * @return OrderListDao
     */
    public OrderListDao connectOrderListDao(){
        return new OrderListDaoImpl(connection);
    }

    /**
     * Commit changes
     * @throws TransactionManagerException
     */
    public void commit() throws TransactionManagerException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not commit " + e);
        }
    }

    /**
     * rollback
     * @throws TransactionManagerException
     */
    public void rollback() throws TransactionManagerException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not rollback " + e);
        }
    }

    /**
     * returns connection to Connection pool
     * @throws TransactionManagerException
     */
    public void endTransaction() throws TransactionManagerException {
        try {
            connection.setAutoCommit(true);
            ConnectionPool.getInstance().returnConnection(connection);
            connection = null;
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not finish transaction " + e);
        }
    }

}
