package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
/**
 * LoginCommand class has UserService field
 */
public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String LOGIN_ERROR_MESSAGE = "login.error";
    private static final String LOGIN_PATH = "path.page.login";
    private UserServiceImpl userService = new UserServiceImpl();

    /**
     * execute method checks if user exists, if user is admin and saves current user in session
     * @param request contains user login and password
     * @see Command#execute(HttpServletRequest)
     */
    public String execute(HttpServletRequest request){
        String page = null;
        String login = request.getParameter(CommandParameterHolder.PARAM_LOGIN.getName());
        String password = request.getParameter(CommandParameterHolder.PARAM_PASSWORD.getName());
        request.removeAttribute(CommandParameterHolder.PARAM_LOGIN.getName());
        request.removeAttribute(CommandParameterHolder.PARAM_PASSWORD.getName());
        if ((login != null)&&(password != null)) {
            try {
                if (userService.checkLogin(login, password)) {
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_USER.getName(), userService.findById(login));
                    boolean isAdmin = userService.isAdmin(login);
                    if (isAdmin) {
                        ForwardAdminCommand forwardAdminCommand = new ForwardAdminCommand();
                        page = forwardAdminCommand.execute(request);
                    } else {
                        ForwardMainCommand forwardMainCommand = new ForwardMainCommand();
                        page = forwardMainCommand.execute(request);
                    }
                    request.getSession().removeAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName());
                } else {
                    logger.log(Level.WARN, "checkLogin returned false");
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName(), LOGIN_ERROR_MESSAGE);
                    page = LOGIN_PATH;
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                request.getSession().setAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName(), LOGIN_ERROR_MESSAGE);
                page = LOGIN_PATH;
            }
        }
        return page;
    }
}
