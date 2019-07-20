package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    public String execute(HttpServletRequest request){
        return "/jsp/login.jsp";
    }
}
