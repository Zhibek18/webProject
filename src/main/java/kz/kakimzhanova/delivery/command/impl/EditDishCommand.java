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

public class EditDishCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_DISH_ERROR_MESSAGE = "editMenu.error";
    private static final String EDIT_MENU_PATH = "path.page.editMenu";
    private DishService dishService = new DishServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName());
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_ADDED.getName());

        try {
            String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
            String dishNameRu = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME_RU.getName());
            String dishNameEn = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME_EN.getName());
            String descriptionRu = request.getParameter(CommandParameterHolder.PARAM_DESCRIPTION_RU.getName());
            String descriptionEn = request.getParameter(CommandParameterHolder.PARAM_DESCRIPTION_EN.getName());
            BigDecimal dishPrice = new BigDecimal(request.getParameter(CommandParameterHolder.PARAM_DISH_PRICE.getName()));
            List<Dish> dishes = (ArrayList<Dish>)request.getSession().getAttribute(CommandParameterHolder.PARAM_MENU.getName());
            Dish newDish = new Dish(dishName,dishNameRu, dishNameEn, descriptionRu, descriptionEn, dishPrice);
            if (dishService.editDish(newDish)){
                for (Dish dish : dishes){
                    if (dish.getDishName().equals(dishName)){
                        dish.setDishNameRu(dishNameRu);
                        dish.setDishNameEn(dishNameEn);
                        dish.setDescriptionRu(descriptionRu);
                        dish.setDescriptionEn(descriptionEn);
                        dish.setPrice(dishPrice);
                    }
                }
                request.getSession().setAttribute(CommandParameterHolder.PARAM_MENU.getName(), dishes);
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName());
            } else {
                logger.log(Level.WARN, "editDishPrice returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName(), EDIT_DISH_ERROR_MESSAGE);
            }

        }catch (ServiceException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName(), EDIT_DISH_ERROR_MESSAGE);
        }
        return EDIT_MENU_PATH;
    }
}
