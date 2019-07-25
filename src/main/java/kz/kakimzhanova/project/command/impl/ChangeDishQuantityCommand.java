package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.Order;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.service.OrderListService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ChangeDishQuantityCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderListService orderListService = new OrderListService();
    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order)request.getSession().getAttribute("order");
        List<OrderedDish> orderList = order.getOrderList();
        int orderId = order.getOrderId();
        String dishName = request.getParameter("dishName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (orderListService.changeDishQuantity(orderId, dishName, quantity)) {
            logger.log(Level.DEBUG, "order_id="+orderId+" dishName="+dishName + " quantity="+quantity);
            for (OrderedDish dish : orderList) {
                if ((dish.getOrderId() == orderId) && (dish.getDishName().equals(dishName))) {
                    dish.setQuantity(quantity);
                }
            }
        } else {
            request.setAttribute("changeQuantityError", "change quantity error");
        }
        return "/jsp/order.jsp";
    }
}
