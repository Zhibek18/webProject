package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.service.OrderListService;
import kz.kakimzhanova.project.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddDishCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderListService orderListService = new OrderListService();
    private OrderService orderService = new OrderService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        int orderId;
        Order order;
        String login = request.getSession().getAttribute("login").toString();
        Object attrOrderId = request.getSession().getAttribute("orderId");
        if (attrOrderId != null) {
            orderId = (Integer) attrOrderId;
        } else {
            order = orderService.newOrder(login);
            orderId = order.getOrderId();
            request.getSession().setAttribute("orderId", orderId);
            request.getSession().setAttribute("order", order);
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
