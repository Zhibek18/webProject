package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ChangePasswordCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_OLD_PASSWORD = "oldPassword";
    private static final String PARAM_NEW_PASSWORD = "newPassword";
    UserService service = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(PARAM_LOGIN);
        String oldPassword = request.getParameter(PARAM_OLD_PASSWORD);
        String newPassword = request.getParameter(PARAM_NEW_PASSWORD);
        if (service.changePassword(login, oldPassword, newPassword)){
            page = "/jsp/main.jsp";
        } else {
            request.setAttribute("updateError", "wrong login or password");
            page = "/jsp/changePassword.jsp";
        }
        return page;
    }
}
