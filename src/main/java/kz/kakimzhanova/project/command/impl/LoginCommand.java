package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.UserService;
import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private UserService service = new UserService();
    public String execute(HttpServletRequest request){
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        boolean isAdmin = service.isAdmin(login);
        if (service.checkLogin(login, pass)){
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("isAdmin",isAdmin);
            page = "/jsp/main.jsp";
        } else {
            request.setAttribute("errorLoginPathMessage", "Incorrect login or password");
            page = "/jsp/login.jsp";
        }
        return page;
    }
}
