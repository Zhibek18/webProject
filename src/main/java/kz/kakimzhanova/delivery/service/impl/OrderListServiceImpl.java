package kz.kakimzhanova.delivery.service.impl;

import kz.kakimzhanova.delivery.dao.OrderListDao;
import kz.kakimzhanova.delivery.dao.impl.OrderListDaoImpl;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.DaoException;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderListServiceImpl implements OrderListService {
    private static Logger logger = LogManager.getLogger();
    private static OrderListDao orderListDao = new OrderListDaoImpl();

    @Override
    public List<OrderedDish> showOrderList(int orderId) throws ServiceException {
        List<OrderedDish> orderList;
        try {
            orderList = orderListDao.findByOrderId(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public boolean addDish(int orderId, String dishName) throws ServiceException {
        boolean isAdded;
        try {
            if (orderListDao.findByOrderIdAndDishName(orderId, dishName) != null){
                isAdded = orderListDao.quantityIncrement(orderId, dishName);
            } else {
                isAdded =orderListDao.create(orderId, dishName);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isAdded;
    }
    @Override
    public boolean deleteDish(int orderId, String dishName) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = orderListDao.delete(orderId, dishName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isDeleted;
    }
    @Override
    public boolean changeDishQuantity(int orderId, String dishName, int newQuantity) throws ServiceException {
        boolean isChanged = false;
        if (newQuantity > 0) {
            try {
                isChanged = orderListDao.updateQuantity(orderId, dishName, newQuantity);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isChanged;
    }
}
