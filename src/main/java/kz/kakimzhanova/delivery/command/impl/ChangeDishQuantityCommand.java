package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.service.impl.OrderListServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ChangeDishQuantityCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderListService orderListService = new OrderListServiceImpl();
    private static final String CHANGE_QUANTITY_ERROR_MESSAGE = "changeQuantity.error";
    private static final String ORDER_PATH = "path.page.order";
    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order)request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER.getName());
        List<OrderedDish> orderList = order.getOrderList();
        int orderId = order.getOrderId();
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        try {
            int quantity = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_DISH_QUANTITY.getName()));

            if (orderListService.changeDishQuantity(orderId, dishName, quantity)) {
                for (OrderedDish dish : orderList) {
                    if ((dish.getOrderId() == orderId) && (dish.getDishName().equals(dishName))) {
                        dish.setQuantity(quantity);
                    }
                }
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_CHANGE_QUANTITY_ERROR.getName());
            } else {
                logger.log(Level.WARN, "changeDishQuantity returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_CHANGE_QUANTITY_ERROR.getName(), CHANGE_QUANTITY_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_CHANGE_QUANTITY_ERROR.getName(), CHANGE_QUANTITY_ERROR_MESSAGE);
        }
        return ORDER_PATH;
    }
}
