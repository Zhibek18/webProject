package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.Order;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.OrderService;
import kz.kakimzhanova.delivery.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * DeleteOrderCommand class
 */
public class DeleteOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private OrderService orderService = new OrderServiceImpl();
    private static final String DELETE_ORDER_ERROR = "deleteOrder.error";
    private static final String INDEX_PATH = "path.page.index";

    /**
     * execute method deletes existing order from orders table in database
     * @param request contains order parameters
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = INDEX_PATH;
        try {
            int orderId = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_ORDER_ID.getName()));
            if (orderService.deleteOrder(orderId)) {
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER_ID.getName());
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER.getName());
                request.removeAttribute(CommandParameterHolder.PARAM_DELETE_ORDER_ERROR.getName());
                User user = (User)request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
                boolean isAdmin = false;
                if (user != null) {
                    isAdmin = user.isAdmin();
                } else {
                    logger.log(Level.ERROR, "User attribute is empty");
                }
                if (isAdmin){
                    ForwardAdminCommand forwardAdminCommand = new ForwardAdminCommand();
                    page = forwardAdminCommand.execute(request);
                } else {
                    logger.log(Level.ERROR, "Someone tried to delete order from admin page");
                }
            } else {
                logger.log(Level.ERROR, "deleteOrder returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_DELETE_ORDER_ERROR.getName(), DELETE_ORDER_ERROR);
            }
        }catch (NumberFormatException | ServiceException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_DELETE_ORDER_ERROR.getName(), DELETE_ORDER_ERROR);
        }

        return page;
    }
}
