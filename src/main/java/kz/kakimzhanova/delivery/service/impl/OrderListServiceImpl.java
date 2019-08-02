package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.dao.impl.OrderListDaoImpl;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManager;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManagerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderListServiceImpl implements OrderListService {
    private static Logger logger = LogManager.getLogger();
    private static final String ROLLBACK_ERROR = "Rollback failed: ";
    @Override
    public List<OrderedDish> showOrderList(int orderId) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        List<OrderedDish> orderList;
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            orderList = orderListDao.findByOrderId(orderId);
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not show order list for order with id = " + orderId + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction show order list for order with id = " + orderId + ": " + e);
            }
        }
        return orderList;
    }

    @Override
    public boolean addDish(int orderId, String dishName) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isAdded;
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            if (orderListDao.findByOrderIdAndDishName(orderId, dishName) != null){
                isAdded = orderListDao.quantityIncrement(orderId, dishName);
            } else {
                isAdded = orderListDao.create(orderId, dishName, 1);
            }
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not add dish '" + dishName + "' to order with id = " + orderId + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction add dish '" + dishName + "' for order with id = " + orderId + ": " + e);
            }
        }
        return isAdded;
    }
    @Override
    public boolean deleteDish(int orderId, String dishName) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isDeleted;
        try {
            transactionManager.beginTransaction();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            isDeleted = orderListDao.delete(orderId, dishName);
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not delete dish '" + dishName + "' for order with id = " + orderId  + ": " + e);
        }  finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction delete dish '" + dishName + "' for order with id = " + orderId + ": " + e);
            }
        }
        return isDeleted;
    }
    @Override
    public boolean changeDishQuantity(int orderId, String dishName, int newQuantity) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isChanged = false;
        if (newQuantity > 0) {
            try {
                transactionManager.beginTransaction();
                OrderListDao orderListDao = transactionManager.connectOrderListDao();
                isChanged = orderListDao.updateQuantity(orderId, dishName, newQuantity);
                transactionManager.commit();
            } catch (DaoException | TransactionManagerException e) {
                try {
                    transactionManager.rollback();
                } catch (TransactionManagerException ex) {
                    logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
                }
                throw new ServiceException("Could not change quantity for dish '" + dishName + "' for order with id = " + orderId + ": " + e);
            }finally {
                try {
                    transactionManager.endTransaction();
                } catch (TransactionManagerException e) {
                    logger.log(Level.WARN, "Could not end transaction change quantity for dish '" + dishName + "' for order with id = " + orderId + ": " + e);
                }
            }
        }
        return isChanged;
    }
}
