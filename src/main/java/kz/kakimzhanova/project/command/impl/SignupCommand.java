package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignupCommand  implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private UserService service = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        if(!service.checkLogin(login,password)) {
            if ((service.addNewUser(login, password))) {
                page = "/jsp/login.jsp";
            } else {
                request.setAttribute("errorSignUpMessage", "not valid login or password");
                page = "/jsp/signup.jsp";
            }
        }else {
            request.setAttribute("errorSignUpMessage", "login is already taken. Try again:");
            page = "/jsp/signup.jsp";
        }
        return page;
    }
}
