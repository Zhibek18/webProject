package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Dish;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.DishService;
import kz.kakimzhanova.delivery.service.impl.DishServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddDishToMenuCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_MENU_PATH = "path.page.editMenu";
    private DishService dishService = new DishServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        try{
            BigDecimal price = new BigDecimal(request.getParameter(CommandParameterHolder.PARAM_DISH_PRICE.getName()));
            Dish dish = new Dish(dishName, price);

            request.getSession().removeAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName());
            if (dishService.addDish(dish)){
                List<Dish> dishes = (ArrayList<Dish>)request.getSession().getAttribute(CommandParameterHolder.PARAM_MENU.getName());
                dishes.add(dish);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_MENU.getName(), dishes);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_ADDED.getName());
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName());
            } else {
                logger.log(Level.WARN, "addDish returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_ADDED.getName());
            }
        } catch (ServiceException|NumberFormatException e){
            logger.log(Level.ERROR, e + CommandParameterHolder.PARAM_NOT_ADDED.getName() + CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
            request.getSession().setAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
        }
        return EDIT_MENU_PATH;
    }
}
