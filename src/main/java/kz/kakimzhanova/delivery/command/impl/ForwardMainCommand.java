package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * ForwardMainCommand class has OrderService field
 */
public class ForwardMainCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String MAIN_PATH = "path.page.main";
    private static final String SHOW_ORDERS_ERROR_MESSAGE = "showOrders.error";
    private OrderService orderService = new OrderServiceImpl();

    /**
     * execute method loads all orders of current user to sessions orders attribute and forwards to main page
     * @param request provides access to session attributes
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
        String login = null;
        if (user != null) {
            login = user.getLogin();
        } else {
            logger.log(Level.ERROR, "Got null user attribute");
        }
        try {
            List<Order> orders = orderService.findOrdersByLogin(login);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDERS.getName(), orders);
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find orders for " + login + ": " + e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName(), SHOW_ORDERS_ERROR_MESSAGE);
        }
        return MAIN_PATH;
    }
}
