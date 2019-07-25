package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class UpdateOrderTotalCostCommand implements Command {
    private OrderService orderService = new OrderService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Order order = (Order)request.getSession().getAttribute("order");
        int orderId = order.getOrderId();
        float totalCost = Float.parseFloat(request.getParameter("totalCost"));
        if (totalCost != 0.0){
            if (orderService.updateTotalCost(orderId, totalCost)) {
                request.getSession().setAttribute("orderId", null);
                request.getSession().setAttribute("order", null);
                request.setAttribute("confirmedOrder", orderService.findOrderById(orderId));
                page = "/jsp/check.jsp";
            } else {
                request.setAttribute("orderConfirmError", "Order confirm error");
                page = "/jsp/order.jsp";
            }
        } else {
            request.setAttribute("orderConfirmError", "Your order is empty");
            page = "/jsp/order.jsp";
        }
        return page;
    }
}
