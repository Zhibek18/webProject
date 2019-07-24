package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Dish;
import kz.kakimzhanova.project.service.DishService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMenuCommand implements Command {
    DishService service = new DishService();
    @Override
    public String execute(HttpServletRequest request) {
        List<Dish> dishes = service.findAllDishes();
        request.getSession().setAttribute("menu", dishes);
        return "/jsp/menu.jsp";
    }
}
