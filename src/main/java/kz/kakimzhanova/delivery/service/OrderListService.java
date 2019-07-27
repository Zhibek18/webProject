package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ServiceException;

import java.util.List;

public interface OrderListService {
    List<OrderedDish> showOrderList(int orderId) throws ServiceException;
    boolean addDish(int orderId, String dishName) throws ServiceException;
    boolean deleteDish(int orderId, String dishName) throws ServiceException;
    boolean changeDishQuantity(int orderId, String dishName, int newQuantity) throws ServiceException;
}
