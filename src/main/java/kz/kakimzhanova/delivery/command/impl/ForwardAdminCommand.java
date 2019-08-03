package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ForwardAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADMIN_PATH = "path.page.admin";
    private static final String INDEX_PATH = "path.page.index";
    private static final String SHOW_ORDERS_ERROR_MESSAGE = "showOrders.error";
    private OrderService orderService = new OrderServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        if ((Boolean) request.getSession().getAttribute(CommandParameterHolder.PARAM_IS_ADMIN.getName())) {
            try {
                List<Order> orders = orderService.findAllOrders();
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDERS.getName(), orders);
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName());
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Could not show orders: " + e);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName(),SHOW_ORDERS_ERROR_MESSAGE );
            }
            return ADMIN_PATH;
        } else {
            return INDEX_PATH;
        }
    }
}
