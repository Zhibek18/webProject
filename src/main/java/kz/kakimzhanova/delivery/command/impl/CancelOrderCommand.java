package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;

import javax.servlet.http.HttpServletRequest;
/**
 *  CancelOrderCommand class
 */
public class CancelOrderCommand implements Command {
    private static final String MENU_PATH = "path.page.menu";
    /**
     * execute method removes orderList session attribute
     * @param request - provides access to the orderList session attribute
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
        return MENU_PATH;
    }
}
