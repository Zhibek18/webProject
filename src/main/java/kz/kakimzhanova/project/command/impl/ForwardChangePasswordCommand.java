package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangePasswordCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/jsp/changePassword.jsp";
    }
}
