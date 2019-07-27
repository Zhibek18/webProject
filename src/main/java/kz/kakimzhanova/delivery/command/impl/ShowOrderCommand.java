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


public class ShowOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ORDER_ERROR = "order.error";
    private static final String ORDER_PATH = "path.page.order";
    private OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Object orderIdObject = request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName());
        try {
            if (orderIdObject!= null) {
                int orderId = Integer.parseInt(orderIdObject.toString());
                Order order = orderService.findOrderById(orderId);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER.getName(), order);
            }
        }catch (NumberFormatException | ServiceException e){
            logger.log(Level.ERROR, e);
            request.setAttribute(CommandParameterHolder.PARAM_ORDER_ERROR.getName(),ORDER_ERROR);
        }
        return ORDER_PATH;
    }
}
