package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;

import javax.servlet.http.HttpServletRequest;

public class CancelOrderCommand implements Command {
    private static final String MENU_PATH = "path.page.menu";
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().removeAttribute(CommandParameterHolder.PARAM_ORDER_LIST.getName());
        return MENU_PATH;
    }
}
