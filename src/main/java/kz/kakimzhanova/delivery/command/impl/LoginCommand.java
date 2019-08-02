package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import kz.kakimzhanova.delivery.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String LOGIN_ERROR_MESSAGE = "login.error";
    private static final String MAIN_PATH = "path.page.main";
    private static final String LOGIN_PATH = "path.page.login";
    private static final String ADMIN_PATH = "path.page.admin";
    private static final String SHOW_ORDERS_ERROR_MESSAGE = "showOrders.error";
    private UserServiceImpl userService = new UserServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    public String execute(HttpServletRequest request){
        String page = null;
        String login = request.getParameter(CommandParameterHolder.PARAM_LOGIN.getName());
        String password = request.getParameter(CommandParameterHolder.PARAM_PASSWORD.getName());
        request.removeAttribute(CommandParameterHolder.PARAM_LOGIN.getName());
        request.removeAttribute(CommandParameterHolder.PARAM_PASSWORD.getName());
        if ((login != null)&&(password != null)) {
            try {
                if (userService.checkLogin(login, password)) {
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_LOGIN.getName(), login);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_USER.getName(), userService.findById(login));
                    boolean isAdmin = userService.isAdmin(login);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_IS_ADMIN.getName(), isAdmin);
                    List<Order> orders;
                    if (isAdmin) {
                        orders = orderService.findAllOrders();
                        page = ADMIN_PATH;
                    } else {
                        orders = orderService.findOrdersByLogin(login);
                        page = MAIN_PATH;
                    }
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDERS.getName(), orders);
                    request.getSession().removeAttribute(CommandParameterHolder.PARAM_SHOW_ORDERS_ERROR.getName());
                    request.getSession().removeAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName());
                } else {
                    logger.log(Level.WARN, "checkLogin returned false");
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName(), LOGIN_ERROR_MESSAGE);
                    page = LOGIN_PATH;
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName(), LOGIN_ERROR_MESSAGE);
                page = LOGIN_PATH;
            }
        }
        return page;
    }
}
