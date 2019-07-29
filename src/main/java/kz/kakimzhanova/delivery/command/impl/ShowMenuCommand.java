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

public class ShowMenuCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String MENU_PATH = "path.page.menu";
    private static final String MAIN_PATH = "path.page.main";
    private static final String MENU_ERROR_MESSAGE = "showMenu.Error";
    private DishService service = new DishServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        List<Dish> dishes;
        try {
            dishes = service.findAllDishes();
            request.getSession().setAttribute(CommandParameterHolder.PARAM_MENU.getName(), dishes);
            page = MENU_PATH;
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_SHOW_MENU_ERROR.getName());
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_CONFIRMED_ORDER.getName());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_SHOW_MENU_ERROR.getName(), MENU_ERROR_MESSAGE);
            page = MAIN_PATH;
        }
        return page;
    }
}
