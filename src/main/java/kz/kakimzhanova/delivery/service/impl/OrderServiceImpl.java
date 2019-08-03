package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.OrderDao;
import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.exception.TransactionManagerException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.transaction.OrderTransactionManager;
import kz.kakimzhanova.delivery.transaction.impl.OrderTransactionManagerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static Logger logger = LogManager.getLogger();
    private static final int MAX_STATUS_VALUE = 2;
    private static final int MIN_STATUS_VALUE = 0;
    private static final String ROLLBACK_ERROR = "Rollback failed: ";
    @Override
    public List<Order> findAllOrders() throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        List<Order> orders;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            orders = orderDao.findAll();
            if (orders != null) {
                for (Order order : orders) {
                    order.setOrderList(orderListDao.findByOrderId(order.getOrderId()));
                }
                transactionManager.commit();
            } else {
                try {
                    transactionManager.rollback();
                } catch (TransactionManagerException ex) {
                    logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
                }
                throw new ServiceException("Could not find all orders: orderDao returned null");
            }

        } catch (TransactionManagerException| DaoException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not find all orders: " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction findAll orders" + e);
            }
        }
        return orders;
    }

    @Override
    public boolean deleteOrder(int orderId) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isDeleted;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            isDeleted = orderDao.delete(orderId);
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not delete order with id: " + orderId + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction delete order with id: " + orderId + ": " + e);
            }
        }
        return isDeleted;
    }
    @Override
    public Order findOrderById(int orderId) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order order;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            order = orderDao.findById(orderId);
            if (order != null){
                order.setOrderList(orderListDao.findByOrderId(order.getOrderId()));
                transactionManager.commit();
            } else {
                try {
                    transactionManager.rollback();
                } catch (TransactionManagerException ex) {
                    logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
                }
                throw new ServiceException("Could not find order with id = " + orderId + " orderDao findById returned null");
            }
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not find order with id = " + orderId + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction find order with id = " + orderId + ": " + e);
            }
        }
        return order;
    }
    @Override
    public boolean updateTotalCost(int orderId, BigDecimal totalCost) throws ServiceException {
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isUpadted;
        try{
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            isUpadted = orderDao.updateTotalCost(orderId, totalCost);
            transactionManager.commit();
        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not update cost for order with id = " + orderId + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction update cost for order with id = " + orderId + ": " + e);
            }
        }
        return isUpadted;
    }
    @Override
    public boolean updateOrderStatus(int orderId, int status) throws ServiceException{
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        boolean isConfirmed;
        try{
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            if (isValidStatus(status)) {
                isConfirmed = orderDao.updateStatus(orderId, status);
                transactionManager.commit();
            } else {
                throw new ServiceException("Not valid status:" + status);
            }
        }catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not update order status for order with id = " + orderId + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction update order status for order with id = " + orderId + ": " + e);
            }
        }
        return isConfirmed;
    }

    private boolean isValidStatus(int status){
        return ((status >= MIN_STATUS_VALUE)&&(status <= MAX_STATUS_VALUE));
    }
    @Override
    public List<Order> findOrdersByLogin(String login) throws ServiceException{
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        List<Order> orders;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            orders = orderDao.findByLogin(login);
            if (orders != null){
                for (Order order : orders){
                    order.setOrderList(orderListDao.findByOrderId(order.getOrderId()));
                }
                transactionManager.commit();
            } else {
                try {
                    transactionManager.rollback();
                } catch (TransactionManagerException ex) {
                    logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
                }
                throw new ServiceException("Could not find orders where login = " + login + ": orderDao findOrdersByLogin returned null");
            }

        } catch (DaoException | TransactionManagerException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not find orders where login = " + login + ": " + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not end transaction find orders where login = " + login + ": " + e);
            }
        }
        return orders;
    }
    @Override
    public Order createOrder(String login, List<OrderedDish> orderList) throws ServiceException{
        OrderTransactionManager transactionManager = new OrderTransactionManagerImpl();
        Order order;
        try {
            transactionManager.beginTransaction();
            OrderDao orderDao = transactionManager.connectOrderDao();
            OrderListDao orderListDao = transactionManager.connectOrderListDao();
            order = orderDao.create(login);
            if (order != null) {
                int orderId = order.getOrderId();
                for (OrderedDish dish : orderList){
                    orderListDao.create(orderId, dish.getDishName(), dish.getQuantity());
                }
                transactionManager.commit();
            } else {
                throw new ServiceException("orderDao.create returned false");
            }
        } catch (TransactionManagerException| DaoException e) {
            try {
                transactionManager.rollback();
            } catch (TransactionManagerException ex) {
                logger.log(Level.ERROR, ROLLBACK_ERROR + ex);
            }
            throw new ServiceException("Could not create order:" + e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (TransactionManagerException e) {
                logger.log(Level.WARN, "Could not finish transaction correctly: " + e);
            }
        }
        return order;
    }
}
