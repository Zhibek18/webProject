package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignupCommand  implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_STREET = "street";
    private static final String PARAM_HOUSE = "house";
    private static final String PARAM_APARTMENT = "apartment";
    private static final String PARAM_PHONE = "phone";
    private UserService service = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String street = request.getParameter(PARAM_STREET);
        int house = Integer.parseInt(request.getParameter(PARAM_HOUSE));
        int apartment = Integer.parseInt(request.getParameter(PARAM_APARTMENT));
        String phone = request.getParameter(PARAM_PHONE);
        if(!service.checkLogin(login,password)) {
            if ((service.addNewUser(login, password, firstName, street, house, apartment, phone))) {
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
