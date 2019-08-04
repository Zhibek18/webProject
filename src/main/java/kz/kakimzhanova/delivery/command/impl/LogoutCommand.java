package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;
/**
 * LogoutCommand class
 */
public class LogoutCommand implements Command {
    private static final String INDEX_PATH = "path.page.index";

    /**
     * execute method
     * invalidates current session
     */
    public String execute(HttpServletRequest request){
        request.getSession().invalidate();
        return INDEX_PATH;
    }
}
