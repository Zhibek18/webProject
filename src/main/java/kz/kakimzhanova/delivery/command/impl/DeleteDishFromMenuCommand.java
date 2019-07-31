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
import java.util.ArrayList;
import java.util.List;

public class DeleteDishFromMenuCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_MENU_PATH = "path.page.editMenu";
    private static final String DELETE_DISH_ERROR_MESSAGE = "deleteDish.error";
    private DishService dishService = new DishServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName());
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_ADDED.getName());
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        List<Dish> dishes = (ArrayList<Dish>)request.getSession().getAttribute(CommandParameterHolder.PARAM_MENU.getName());
        Dish deletedDish = null;
        try{
            if (dishService.delete(dishName)) {
                for (Dish dish : dishes) {
                    if (dish.getDishName().equals(dishName)) {
                        deletedDish = dish;
                        break;
                    }
                }
                dishes.remove(deletedDish);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_MENU.getName(), dishes);
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName());
            } else {
                logger.log(Level.WARN, "delete dish returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName(), DELETE_DISH_ERROR_MESSAGE);
            }
        } catch (ServiceException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName(), DELETE_DISH_ERROR_MESSAGE);
        }
        return EDIT_MENU_PATH;
    }
}
