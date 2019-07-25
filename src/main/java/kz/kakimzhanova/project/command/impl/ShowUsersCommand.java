package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowUsersCommand implements Command {
    private UserService service = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        if ((Boolean) request.getSession().getAttribute("isAdmin")) {
            List<User> users = service.findAllUsers();
            List<String> userNames = new ArrayList<>();
            for (User user : users) {
                userNames.add(user.getLogin());
            }
            request.setAttribute("userNames", userNames);
            page = "/jsp/users.jsp";
        } else {
            page = "/jsp/main.jsp";
        }
        return page;
    }
}
