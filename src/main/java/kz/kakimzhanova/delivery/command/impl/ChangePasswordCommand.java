package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.UserService;
import kz.kakimzhanova.delivery.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String MAIN_PATH = "path.page.main";
    private static final String CHANGE_PASSWORD_PATH = "path.page.changePassword";
    private static final String NOT_VALID_ERROR = "notValidLogin.error";
    private static final String CHANGE_PASSWORD_ERROR = "changePassword.error";
    private UserService service = new UserServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(CommandParameterHolder.PARAM_LOGIN.getName());
        String oldPassword = request.getParameter(CommandParameterHolder.PARAM_OLD_PASSWORD.getName());
        String newPassword = request.getParameter(CommandParameterHolder.PARAM_NEW_PASSWORD.getName());
        try {
            if (service.changePassword(login, oldPassword, newPassword)) {
                page = MAIN_PATH;
            } else {
                logger.log(Level.WARN, "changePassword returned false");
                request.setAttribute(CommandParameterHolder.PARAM_UPDATE_ERROR.getName(), NOT_VALID_ERROR);
                page = CHANGE_PASSWORD_PATH;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(CommandParameterHolder.PARAM_UPDATE_ERROR.getName(), CHANGE_PASSWORD_ERROR);
            page = CHANGE_PASSWORD_PATH;
        }
        return page;
    }
}
