package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
/**
 * UserConfirmOrderCommand class contains OrderService field
 */
public class UserConfirmOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String CHECK_PATH = "path.page.check";
    private static final String ORDER_PATH = "path.page.order";
    private static final String USER_CONFIRM_ERROR_MESSAGE = "userConfirm.error";
    private OrderService orderService = new OrderServiceImpl();

    /**
     * execute method creates new order and adds current order list to created order
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        List<OrderedDish> orderList = (List<OrderedDish>)request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
        User user = (User) request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
        String login = null;
        if (user != null) {
            login = user.getLogin();
        } else {
            logger.log(Level.ERROR, "Got null user attribute");
        }
        try {
            BigDecimal totalCost = new BigDecimal(request.getParameter(CommandParameterHolder.PARAM_TOTAL_COST.getName()));
            Order order = orderService.createOrder(login, orderList, totalCost);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_CONFIRMED_ORDER.getName(), order);
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
            page = CHECK_PATH;
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName());
        }catch (ServiceException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName(), USER_CONFIRM_ERROR_MESSAGE);
            page = ORDER_PATH;
        }
        return page;
    }
}
