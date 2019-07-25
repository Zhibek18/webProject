package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class DeleteOrderCommand implements Command {
    private OrderService orderService = new OrderService();
    @Override
    public String execute(HttpServletRequest request) {
        int orderId = (Integer)request.getSession().getAttribute("orderId");
        if (orderService.deleteOrder(orderId)){
            request.getSession().setAttribute("orderList", null);
            request.getSession().setAttribute("orderId", null);
            request.getSession().setAttribute("order", null);
        } else {
            request.setAttribute("deleteOrderError", "Delete order error");
        }
        return "/jsp/menu.jsp";
    }
}
