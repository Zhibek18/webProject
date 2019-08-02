package kz.kakimzhanova.delivery.transaction;

import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;

public interface OrderTransactionManager {
    void beginTransaction() throws TransactionManagerException;
    void endTransaction() throws TransactionManagerException;
    void commit() throws TransactionManagerException;
    void rollback() throws TransactionManagerException;
    OrderDao connectOrderDao();
    OrderListDao connectOrderListDao();
}
