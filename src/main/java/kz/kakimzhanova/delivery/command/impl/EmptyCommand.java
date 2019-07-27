package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    private static final String LOGIN_PATH = "path.page.login";
    public String execute(HttpServletRequest request){
        return LOGIN_PATH;
    }
}
