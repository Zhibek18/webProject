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

public class DeleteOrderedDishCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderListService orderListService = new OrderListServiceImpl();
    private static final String DELETE_DISH_ERROR_MESSAGE = "deleteDish.error";
    private static final String ORDER_PATH = "path.page.order";
    @Override
    public String execute(HttpServletRequest request) {
        int orderId = Integer.parseInt(request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName()).toString());
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        Order order = (Order)request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER.getName());
        List<OrderedDish> orderedDishes = order.getOrderList();
        OrderedDish deletedDish = null;
        try {
            if (orderListService.deleteDish(orderId, dishName)) {
                for (OrderedDish dish : orderedDishes) {
                    if ((dish.getDishName().equals(dishName)) && (dish.getOrderId() == orderId)) {
                        deletedDish = dish;
                        break;
                    }
                }
                orderedDishes.remove(deletedDish);
                order.setOrderList(orderedDishes);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER.getName(), order);
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_DELETE_DISH_ERROR.getName());
            } else {
                logger.log(Level.ERROR, "delete dish returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_DELETE_DISH_ERROR.getName(), DELETE_DISH_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_DELETE_DISH_ERROR.getName(), DELETE_DISH_ERROR_MESSAGE);
        }
        return ORDER_PATH;
    }
}
