package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.OrderedDish;
import kz.kakimzhanova.project.service.OrderListService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowOrderCommand implements Command {
    private OrderListService service = new OrderListService();
    @Override
    public String execute(HttpServletRequest request) {
        int orderId = Integer.parseInt(request.getSession().getAttribute("orderId").toString());
        List<OrderedDish> orderList = service.showOrderList(orderId);
        request.getSession().setAttribute("orderList", orderList);
        return "/jsp/order.jsp";
    }
}
