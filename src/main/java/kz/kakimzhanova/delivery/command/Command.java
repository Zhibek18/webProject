package kz.kakimzhanova.delivery.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Command interface has execute method
 */
public interface Command {
    /**
     * @param request contains command parameters such as user login and password
     * @return next page's path to controller
     */
    String execute(HttpServletRequest request);
}
