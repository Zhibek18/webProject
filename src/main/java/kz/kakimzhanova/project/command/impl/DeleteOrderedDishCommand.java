package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.service.OrderListService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DeleteOrderedDishCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderListService orderListService = new OrderListService();
    @Override
    public String execute(HttpServletRequest request) {
        int orderId = Integer.parseInt(request.getSession().getAttribute("orderId").toString());
        String dishName = request.getParameter("dishName");
        Order order = (Order)request.getSession().getAttribute("order");
        List<OrderedDish> orderedDishes = order.getOrderList();
        OrderedDish deletedDish = null;
        if (orderListService.deleteDish(orderId, dishName)){
            for (OrderedDish dish : orderedDishes){
                if ((dish.getDishName().equals(dishName))&&(dish.getOrderId()==orderId)){
                    deletedDish = dish;
                    break;
                }
            }
            orderedDishes.remove(deletedDish);
            order.setOrderList(orderedDishes);
            request.getSession().setAttribute("order", order);
        } else {
            request.setAttribute("deleteDishError", "delete dish Error");
        }
        return "/jsp/order.jsp";
    }
}
