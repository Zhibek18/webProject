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
 * DeleteUserCommand class has UserService field
 */
public class DeleteUserCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String DELETE_USER_ERROR_MESSAGE = "deleteUser.error";
    private static final String MAIN_PATH = "path.page.main";
    private static final String INDEX_PATH = "path.page.index";
    private UserService service = new UserServiceImpl();

    /**
     * execute method deletes current user from users table in database
     * @param request contains user parameters
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        User user = (User)request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
        String login = null;
        if (user != null) {
            login = user.getLogin();
        } else {
            logger.log(Level.ERROR, "null user attribute");
        }
        try {
            if (service.deleteUser(login)) {
                page = INDEX_PATH;
                request.getSession().removeAttribute(CommandParameterHolder.PARAM_DELETE_USER_ERROR.getName());
            } else {
                logger.log(Level.ERROR, "deleteUser returned false");
                page = MAIN_PATH;
                request.getSession().setAttribute(CommandParameterHolder.PARAM_DELETE_USER_ERROR.getName(), DELETE_USER_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            page = MAIN_PATH;
            request.getSession().setAttribute(CommandParameterHolder.PARAM_DELETE_USER_ERROR.getName(), DELETE_USER_ERROR_MESSAGE);
        }
        return page;
    }
}
