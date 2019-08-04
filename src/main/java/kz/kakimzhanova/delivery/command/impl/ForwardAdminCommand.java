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
 * ForwardAdminCommand class has OrderService field
 */
public class ForwardAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADMIN_PATH = "path.page.admin";
    private static final String INDEX_PATH = "path.page.index";
    private static final String SHOW_ORDERS_ERROR_MESSAGE = "showOrders.error";
    private OrderService orderService = new OrderServiceImpl();

    /**
     * execute method checks if user is admin, then loads all orders from orders table with order lists
     * @param request provides access to session attributes
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = INDEX_PATH;
        User user = (User)request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
        if (user != null) {
            if (user.isAdmin()) {
                try {
                    List<Order> orders = orderService.findAllOrders();
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDERS.getName(), orders);
                    request.getSession().removeAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName());
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, "Could not show orders: " + e);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName(), SHOW_ORDERS_ERROR_MESSAGE);
                }
                page = ADMIN_PATH;
            } else {
                logger.log(Level.ERROR, "Common user tried to get admin page");
                page = INDEX_PATH;
            }
        } else {
            logger.log(Level.ERROR, "isAdmin field is null");
        }
        return page;
    }
}
