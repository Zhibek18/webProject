package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowOrdersCommand implements Command {
    OrderService service = new OrderService();
    @Override
    public String execute(HttpServletRequest request) {
        List<Order> orders = service.findAllOrders();
        request.getSession().setAttribute("orders",orders);
        return "/jsp/orders.jsp";
    }
}
