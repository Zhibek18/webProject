package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderService orderService = new OrderServiceImpl();
    private static final String DELETE_ORDER_ERROR = "deleteOrder.error";
    private static final String MENU_PATH = "path.page.menu";
    @Override
    public String execute(HttpServletRequest request) {
        try {
            int orderId = (Integer) request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName());
            if (orderService.deleteOrder(orderId)) {
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName(), null);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER.getName(), null);
            } else {
                logger.log(Level.ERROR, "deleteOrder returned false");
                request.setAttribute(CommandParameterHolder.PARAM_DELETE_ORDER_ERROR.getName(), DELETE_ORDER_ERROR);
            }
        }catch (NumberFormatException | ServiceException e){
            logger.log(Level.ERROR, e);
            request.setAttribute(CommandParameterHolder.PARAM_DELETE_ORDER_ERROR.getName(), DELETE_ORDER_ERROR);
        }
        return MENU_PATH;
    }
}
