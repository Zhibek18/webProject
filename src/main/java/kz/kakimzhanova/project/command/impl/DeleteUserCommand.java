package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private UserService service = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getSession().getAttribute(PARAM_NAME_LOGIN).toString();
        if (service.deleteUser(login)){
            page = "/index.jsp";
        } else {
            page = "/jsp/main.jsp";
            request.setAttribute("deleteError", "Couldn't delete user");
        }

        return page;
    }
}
