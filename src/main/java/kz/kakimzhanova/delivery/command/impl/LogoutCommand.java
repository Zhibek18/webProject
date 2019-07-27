package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    private static final String INDEX_PATH = "path.page.index";
    public String execute(HttpServletRequest request){
        request.getSession().invalidate();
        return INDEX_PATH;
    }
}
