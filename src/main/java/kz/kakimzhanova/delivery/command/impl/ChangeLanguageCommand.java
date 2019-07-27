package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(CommandParameterHolder.PARAM_LANGUAGE.getName());
        request.getSession().setAttribute(CommandParameterHolder.PARAM_LANGUAGE.getName(), language);
        return request.getParameter(CommandParameterHolder.PARAM_PAGE.getName());
    }
}
