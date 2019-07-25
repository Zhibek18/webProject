package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.service.OrderService;

import javax.servlet.http.HttpServletRequest;


public class ShowOrderCommand implements Command {
    private OrderService orderService = new OrderService();

    @Override
    public String execute(HttpServletRequest request) {
        Object orderIdObject = request.getSession().getAttribute("orderId");
        if (orderIdObject != null) {
            int orderId = Integer.parseInt(orderIdObject.toString());
            Order order = orderService.findOrderById(orderId);
            request.getSession().setAttribute("order", order);
        }
        return "/jsp/order.jsp";
    }
}
