package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import javax.servlet.http.HttpServletRequest;

/**
 * ShowOrderCommand class
 */
public class ShowOrderCommand implements Command {
    private static final String ORDER_PATH = "path.page.order";
    /**
     * execute method forwards to current order page
     */
    @Override
    public String execute(HttpServletRequest request) {
        return ORDER_PATH;
    }
}
