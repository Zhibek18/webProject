package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ServiceException;
import kz.kakimzhanova.delivery.service.UserService;
import kz.kakimzhanova.delivery.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 *  ChangePasswordCommand class has UserService field
 */
public class ChangePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String UPDATE_USER_PATH = "path.page.updateUser";
    private static final String NOT_VALID_ERROR = "notValidLogin.error";
    private static final String CHANGE_PASSWORD_ERROR = "changePassword.error";
    private UserService service = new UserServiceImpl();

    /**
     * execute method changes password of current user in users table
     * @param request contains new password
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
        String login = null;
        if (user != null) {
            login = user.getLogin();
        } else {
            logger.log(Level.ERROR, "Got null user attribute");
        }
        String oldPassword = request.getParameter(CommandParameterHolder.PARAM_OLD_PASSWORD.getName());
        String newPassword = request.getParameter(CommandParameterHolder.PARAM_NEW_PASSWORD.getName());
        try {
            if (service.changePassword(login, oldPassword, newPassword)) {
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_UPDATE_PASSWORD_ERROR.getName());
            } else {
                logger.log(Level.WARN, "changePassword returned false");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_UPDATE_PASSWORD_ERROR.getName(), NOT_VALID_ERROR);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_UPDATE_PASSWORD_ERROR.getName(), CHANGE_PASSWORD_ERROR);
        }
        return UPDATE_USER_PATH;
    }
}
