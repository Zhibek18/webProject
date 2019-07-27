package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String LOGIN_ERROR_MESSAGE = "login.error";
    private static final String MAIN_PATH = "path.page.main";
    private static final String LOGIN_PATH = "path.page.login";
    private UserServiceImpl service = new UserServiceImpl();
    public String execute(HttpServletRequest request){
        String page = null;
        String login = request.getParameter(CommandParameterHolder.PARAM_LOGIN.getName());
        String password = request.getParameter(CommandParameterHolder.PARAM_PASSWORD.getName());
        if ((login != null)&&(password != null)) {
            try {
                boolean isAdmin = service.isAdmin(login);
                if (service.checkLogin(login, password)) {
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_LOGIN.getName(), login);
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_IS_ADMIN.getName(), isAdmin);
                    page = MAIN_PATH;
                } else {
                    logger.log(Level.WARN, "checkLogin returned false");
                    request.setAttribute(CommandParameterHolder.PARAM_LOGIN_ERROR.getName(), LOGIN_ERROR_MESSAGE);
                    page = LOGIN_PATH;
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return page;
    }
}
