package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;

import javax.servlet.http.HttpServletRequest;
/**
 *  ChangeLanguageCommand class
 */
public class ChangeLanguageCommand implements Command {
    /**
     * execute changes the language attribute to the given
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(CommandParameterHolder.PARAM_LANGUAGE.getName());
        request.getSession().setAttribute(CommandParameterHolder.PARAM_LANGUAGE.getName(), language);
        return request.getParameter(CommandParameterHolder.PARAM_PAGE.getName());
    }
}
