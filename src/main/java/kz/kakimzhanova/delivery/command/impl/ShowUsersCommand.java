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
import java.util.ArrayList;
import java.util.List;

public class ShowUsersCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String USERS_PATH = "path.page.users";
    private static final String MAIN_PATH = "path.page.main";
    private static final String SHOW_USERS_ERROR_MESSAGE = "showUsers.error";
    private UserService service = new UserServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Object isAdminObject = request.getSession().getAttribute(CommandParameterHolder.PARAM_IS_ADMIN.getName());
        try {
            if ((Boolean) isAdminObject) {
                List<User> users = service.findAllUsers();
                List<String> userNames = new ArrayList<>();
                for (User user : users) {
                    userNames.add(user.getLogin());
                }
                request.setAttribute(CommandParameterHolder.PARAM_USER_NAMES.getName(), userNames);
                page = USERS_PATH;
            } else {
                logger.log(Level.WARN, "Not admin user tried to see all users. Login = " + request.getSession().getAttribute(CommandParameterHolder.PARAM_LOGIN.getName()));
                page = MAIN_PATH;
            }
        }catch (ServiceException e){
            logger.log(Level.ERROR, e);
            request.setAttribute(CommandParameterHolder.PARAM_SHOW_USERS_ERROR.getName(), SHOW_USERS_ERROR_MESSAGE);
            page = MAIN_PATH;
        }
        return page;
    }
}
