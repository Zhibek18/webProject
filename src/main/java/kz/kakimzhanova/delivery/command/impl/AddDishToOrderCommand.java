package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.OrderedDish;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderListService;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderListServiceImpl;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddDishToOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String MENU_PATH = "path.page.menu";
    @Override
    public String execute(HttpServletRequest request) {
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        String dishNameRu = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME_RU.getName());
        String dishNameEn = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME_EN.getName());
        String descriptionRu = request.getParameter(CommandParameterHolder.PARAM_DESCRIPTION_RU.getName());
        String descriptionEn = request.getParameter(CommandParameterHolder.PARAM_DESCRIPTION_EN.getName());
        BigDecimal price = new BigDecimal(request.getParameter(CommandParameterHolder.PARAM_DISH_PRICE.getName()));
        List<OrderedDish> orderList = (List<OrderedDish>) request.getSession().getAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
        if (orderList != null){
            for (OrderedDish dish : orderList){
                if (dish.getDishName().equals(dishName)){
                    int oldQuantity = dish.getQuantity();
                    dish.setQuantity(oldQuantity + 1);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_ADDED.getName());
                    return MENU_PATH;
                }
            }
        }else {
            orderList = new ArrayList<>();
        }
        OrderedDish dish = new OrderedDish(0, dishName, dishNameRu, dishNameEn, descriptionRu, descriptionEn, price, 1);
        orderList.add(dish);
        request.getSession().setAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName(), orderList);
        request.getSession().setAttribute(CommandParameterHolder.PARAM_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_ADDED.getName());
        return MENU_PATH;
    }
}
