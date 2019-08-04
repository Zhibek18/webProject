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
/**
 * AddDishToMenuCommand class has DishService field
 */
public class AddDishToMenuCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_MENU_PATH = "path.page.editMenu";
    private DishService dishService = new DishServiceImpl();

    @Override
    /**
     * execute method adds new dish to menu table in database
     * @see Command#execute(HttpServletRequest)
     * @param request - request with the new dish parameters
     */
    public String execute(HttpServletRequest request) {
        String dishName = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME.getName());
        try{
            String dishNameRu = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME_RU.getName());
            String dishNameEn = request.getParameter(CommandParameterHolder.PARAM_DISH_NAME_EN.getName());
            String descriptionRu = request.getParameter(CommandParameterHolder.PARAM_DESCRIPTION_RU.getName());
            String descriptionEn = request.getParameter(CommandParameterHolder.PARAM_DESCRIPTION_EN.getName());
            BigDecimal price = new BigDecimal(request.getParameter(CommandParameterHolder.PARAM_DISH_PRICE.getName()));
            Dish dish = new Dish(dishName,dishNameRu, dishNameEn, descriptionRu, descriptionEn, price);
            if (dishService.addDish(dish)){
                request.getSession().setAttribute(CommandParameterHolder.PARAM_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_ADDED.getName());
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName());
                ForwardEditMenuCommand forwardEditMenuCommand = new ForwardEditMenuCommand();
                return forwardEditMenuCommand.execute(request);
            } else {
                logger.log(Level.WARN, "addDish returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_ADDED.getName());
            }
        } catch (ServiceException|NumberFormatException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_NOT_ADDED.getName(), CommandParameterHolder.PARAM_STATUS_NOT_ADDED.getName());
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_ADDED.getName());
        }
        return EDIT_MENU_PATH;
    }
}
