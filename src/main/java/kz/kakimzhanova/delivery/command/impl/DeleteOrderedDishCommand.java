package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.service.impl.OrderListServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteOrderedDishCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ORDER_PATH = "path.page.order";
    @Override
    public String execute(HttpServletRequest request) {
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        List<OrderedDish> orderedDishes = (List<OrderedDish>) request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
        OrderedDish deletedDish = null;
        for (OrderedDish dish : orderedDishes) {
            if (dish.getDishName().equals(dishName)) {
                deletedDish = dish;
                break;
            }
        }
        orderedDishes.remove(deletedDish);
        request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName(), orderedDishes);
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_DELETE_DISH_ERROR.getName());
        return ORDER_PATH;
    }
}
