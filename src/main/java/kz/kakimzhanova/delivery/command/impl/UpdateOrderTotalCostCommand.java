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

public class UpdateOrderTotalCostCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String CHECK_PATH = "path.page.check";
    private static final String ORDER_PATH = "path.page.order";
    private static final String TOTAL_COST_ERROR_MESSAGE = "order.totalCost.error";
    private static final String EMPTY_ORDER_ERROR_MESSAGE = "emptyOrder.error";
    private OrderService orderService = new OrderServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Order order = (Order)request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER.getName());
        int orderId = order.getOrderId();
        float totalCost = Float.parseFloat(request.getParameter(CommandParameterHolder.PARAM_TOTAL_COST.getName()));
        if (totalCost != 0.0){
            try {
                if (orderService.updateTotalCost(orderId, totalCost)) {
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName(), null);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER.getName(), null);
                    request.setAttribute(CommandParameterHolder.PARAM_CONFIRMED_ORDER.getName(), orderService.findOrderById(orderId));
                    page = CHECK_PATH;
                } else {
                    request.setAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName(), TOTAL_COST_ERROR_MESSAGE);
                    page = ORDER_PATH;
                }
            }catch (ServiceException e){
                logger.log(Level.ERROR, e);
                request.setAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName(), TOTAL_COST_ERROR_MESSAGE);
                page = ORDER_PATH;
            }
        } else {
            request.setAttribute(CommandParameterHolder.PARAM_ORDER_CONFIRM_ERROR.getName(), EMPTY_ORDER_ERROR_MESSAGE);
            page = ORDER_PATH;
        }
        return page;
    }
}