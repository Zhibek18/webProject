package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;
/**
 * EmptyCommand class for debugging
 */
public class EmptyCommand implements Command {
    private static final String LOGIN_PATH = "path.page.login";

    /**
     * execute method forwards to login page
     */
    public String execute(HttpServletRequest request){
        return LOGIN_PATH;
    }
}
