package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.service.OrderListService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DeleteOrderedDishCommand implements Command {
    private OrderListService orderListService = new OrderListService();
    @Override
    public String execute(HttpServletRequest request) {
        int orderId = Integer.parseInt(request.getSession().getAttribute("orderId").toString());
        String dishName = request.getParameter("dishName");
        List<OrderedDish> orderedDishes = (ArrayList)request.getSession().getAttribute("orderList");
        OrderedDish deletedDish = null;
        if (orderListService.deleteDish(orderId, dishName)){
            for (OrderedDish dish : orderedDishes){
                if ((dish.getDishName().equals(dishName))&&(dish.getOrderId()==orderId)){
                    deletedDish = dish;
                    break;
                }
            }
            orderedDishes.remove(deletedDish);
            request.getSession().setAttribute("orderList", orderedDishes);
        } else {
            request.setAttribute("deleteDishError", "delete dish Error");
        }
        return "/jsp/order.jsp";
    }
}
