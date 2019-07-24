package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.OrderListService;
import kz.kakimzhanova.project.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class AddDishCommand implements Command {
    private OrderListService orderListService = new OrderListService();
    private OrderService orderService = new OrderService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        int orderId;
        String login = request.getSession().getAttribute("login").toString();
        Object attrOrderId = request.getSession().getAttribute("orderId");
        if (attrOrderId != null) {
            orderId = (Integer) attrOrderId;
        } else {
            orderId = orderService.newOrder(login).getOrderId();
            request.getSession().setAttribute("orderId", orderId);
        }
        String dishName = request.getParameter("dishName");
        if (orderListService.addDish(orderId, dishName)) {
            request.setAttribute("addingStatus", "element added");
            page = "/jsp/menu.jsp";
        } else {
            request.setAttribute("addingStatus", "error, element not added");
            page = "/jsp/menu.jsp";
        }
        return page;
    }
}
