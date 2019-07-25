package kz.kakimzhanova.project.service;

import kz.kakimzhanova.project.dao.OrderListDao;
import kz.kakimzhanova.project.dao.impl.OrderListDaoImpl;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderListService {
    private static Logger logger = LogManager.getLogger();
    private static OrderListDao orderListDao = new OrderListDaoImpl();

    public List<OrderedDish> showOrderList(int orderId){
        List<OrderedDish> orderList = null;
        try {
            orderList = orderListDao.findByOrderId(orderId);
            logger.log(Level.DEBUG, orderList);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return orderList;
    }

    public boolean addDish(int orderId, String dishName){
        boolean isAdded = false;
        try {
            if (orderListDao.findByOrderIdAndDishName(orderId, dishName) != null){
                isAdded = orderListDao.quantityIncrement(orderId, dishName);
            } else {
                isAdded =orderListDao.create(orderId, dishName);
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return isAdded;
    }
    public boolean deleteDish(int orderId, String dishName){
        boolean isDeleted = false;
        try {
            isDeleted = orderListDao.delete(orderId, dishName);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
        }
        return isDeleted;
    }
    public boolean changeDishQuantity(int orderId, String dishName, int newQuantity){
        boolean isChanged = false;
        if (newQuantity > 0) {
            try {
                isChanged = orderListDao.updateQuantity(orderId, dishName, newQuantity);
            } catch (DaoException e) {
                logger.log(Level.WARN, e);
            }
        }
        return isChanged;
    }
}
