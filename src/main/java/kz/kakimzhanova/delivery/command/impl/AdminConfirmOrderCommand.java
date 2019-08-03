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

public class AdminConfirmOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ADMIN_PATH = "path.page.admin";
    private static final String ORDER_CONFIRM_ERROR_MESSAGE = "orderComfirm.error";
    private static final int ORDER_STATUS_CONFIRMED = 1;
    private OrderService orderService = new OrderServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        try {
            int orderId = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_ORDER_ID.getName()));
            if (orderService.updateOrderStatus(orderId, ORDER_STATUS_CONFIRMED)){
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName());
            } else {
                logger.log(Level.WARN,"confirm order returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName(), ORDER_CONFIRM_ERROR_MESSAGE);
            }
        }catch (NumberFormatException| ServiceException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName(), ORDER_CONFIRM_ERROR_MESSAGE);
        }
        ShowOrdersCommand showOrdersCommand = new ShowOrdersCommand();
        return showOrdersCommand.execute(request);
    }
}
