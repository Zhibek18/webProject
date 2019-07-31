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
import java.util.List;

public class ForwardEditMenuCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String EDIT_MENU_PATH = "path.page.editMenu";
    private static final String EDIT_MENU_ERROR_MESSAGE = "editMenu.error";
    private DishService dishService = new DishServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        List<Dish> dishes;
        try {
            dishes = dishService.findAllDishes();
            request.getSession().setAttribute(CommandParameterHolder.PARAM_MENU.getName(), dishes);
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_EDIT_MENU_ERROR.getName(), EDIT_MENU_ERROR_MESSAGE);
        }
        return EDIT_MENU_PATH;
    }
}
