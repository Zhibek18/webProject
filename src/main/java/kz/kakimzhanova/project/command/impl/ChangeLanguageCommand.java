package kz.kakimzhanova.project.command.impl;

import kz.kakimzhanova.project.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter("language");
        request.getSession().setAttribute("language", language);
        String page = request.getParameter("page");
        return page;
    }
}
