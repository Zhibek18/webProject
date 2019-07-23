package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ChangeAddressCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_STREET = "street";
    private static final String PARAM_HOUSE = "house";
    private static final String PARAM_APARTMENT = "apartment";
    UserService service = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getSession().getAttribute(PARAM_LOGIN).toString();
        String street = request.getParameter(PARAM_STREET);
        int house = Integer.parseInt(request.getParameter(PARAM_HOUSE));
        int apartment = Integer.parseInt(request.getParameter(PARAM_APARTMENT));
        if (service.changeAddress(login, street, house, apartment)){
            page = "/jsp/main.jsp";
        } else {
            request.setAttribute("changeAddressError", "Adress Error");
            page = "/jsp/changeAddress.jsp";
        }
        return page;
    }
}
