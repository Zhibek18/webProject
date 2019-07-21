package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    public String execute(HttpServletRequest request){
        request.getSession().invalidate();
        return "/index.jsp";
    }
}
