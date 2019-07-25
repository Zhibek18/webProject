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
        String page;
        if ((Boolean) request.getSession().getAttribute("isAdmin")) {
            List<Order> orders = service.findAllOrders();
            request.getSession().setAttribute("orders", orders);
            page = "/jsp/orders.jsp";
        } else {
            page = "/jsp/main.jsp";
        }
        return page;
    }
}
