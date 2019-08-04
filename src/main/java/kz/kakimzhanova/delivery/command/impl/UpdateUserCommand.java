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
 * UpdateUserCommand class contains UserService field
 */
public class UpdateUserCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String UPDATE_USER_ERROR_MESSAGE = "updateUser.error";
    private static final String WRONG_INPUT_MESSAGE = "wrongInput.error";
    private static final String UPDATE_USER_PATH = "path.page.updateUser";
    private UserService service = new UserServiceImpl();

    /**
     * execute method updates current users data in users table
     * @param request contains updated user data
     * @see Command#execute(HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = UPDATE_USER_PATH;
        User newUser;
        User user = (User)request.getSession().getAttribute(CommandParameterHolder.PARAM_USER.getName());
        String login = null;
        if (user != null) {
            login = user.getLogin();
        } else {
            logger.log(Level.ERROR, "Got null user attribute");
        }
        String street = request.getParameter(CommandParameterHolder.PARAM_STREET.getName());
        String firstName = request.getParameter(CommandParameterHolder.PARAM_FIRST_NAME.getName());
        String phone = request.getParameter(CommandParameterHolder.PARAM_PHONE.getName());
        try {
            String house = request.getParameter(CommandParameterHolder.PARAM_HOUSE.getName());
            int apartment = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_APARTMENT.getName()));
            newUser = service.updateUser(login, firstName, street, house, apartment, phone);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_USER.getName(), newUser);
            request.getSession().removeAttribute(CommandParameterHolder.PARAM_UPDATE_USER_ERROR.getName());
            ForwardUpdateUserCommand forwardUpdateUserCommand = new ForwardUpdateUserCommand();
            page = forwardUpdateUserCommand.execute(request);
        }catch (NumberFormatException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_UPDATE_USER_ERROR.getName(), WRONG_INPUT_MESSAGE);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_UPDATE_USER_ERROR.getName(), UPDATE_USER_ERROR_MESSAGE);

        }
        return page;
    }
}
