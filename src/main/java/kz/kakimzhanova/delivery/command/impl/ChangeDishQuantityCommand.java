package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 *  ChangeDishQuantityCommand class
 */
public class ChangeDishQuantityCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String CHANGE_QUANTITY_ERROR_MESSAGE = "changeQuantity.error";
    private static final String ORDER_PATH = "path.page.order";

    /**
     * execute method changes quantity field of specified dish in orderList attribute
     * @param request has dish parameters
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        List<OrderedDish> orderList = (List<OrderedDish>)request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        try {
            int quantity = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_DISH_QUANTITY.getName()));
            for (OrderedDish dish : orderList) {
                if (dish.getDishName().equals(dishName)) {
                    dish.setQuantity(quantity);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName(), orderList);
                    return ORDER_PATH;
                }
            }
            logger.log(Level.ERROR,"dish " + dishName + " not found");
            request.getSession().setAttribute(CommandParameterHolder.PARAM_CHANGE_QUANTITY_ERROR.getName(), CHANGE_QUANTITY_ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            logger.log(Level.WARN, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_CHANGE_QUANTITY_ERROR.getName(), CHANGE_QUANTITY_ERROR_MESSAGE);
        }
        return ORDER_PATH;
    }
}
