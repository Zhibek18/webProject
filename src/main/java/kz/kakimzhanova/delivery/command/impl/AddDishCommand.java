package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderListServiceImpl;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddDishCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String MENU_PATH = "path.page.menu";
    private OrderListService orderListService = new OrderListServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        int orderId;
        Order order;
        String login = request.getSession().getAttribute(CommandParameterHolder.PARAM_LOGIN.getName()).toString();
        Object attrOrderId = request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName());
        try {
            if (attrOrderId != null) {
                orderId = (Integer) attrOrderId;
            } else {
                order = orderService.newOrder(login);
                orderId = order.getOrderId();
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName(), orderId);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER.getName(), order);
            }
            String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
            if (orderListService.addDish(orderId, dishName)) {
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_ADDED.getName());
            } else {
                logger.log(Level.ERROR, "addDish returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
            }
        }catch (NumberFormatException | ServiceException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
        }
        return MENU_PATH;
    }
}
