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

public class SignupCommand  implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String LOGIN_PATH = "path.page.login";
    private static final String SIGNUP_PATH = "path.page.signup";
    private static final String LOGIN_NOT_VALID_ERROR_MESSAGE = "notValidLogin.error";
    private static final String LOGIN_TAKEN_ERROR_MESSAGE = "loginTaken.error";
    private static final String WRONG_INPUT_ERROR_MESSAGE = "wrongInput.error";
    private static final String SIGNUP_ERROR_MESSAGE = "signup.error";
    private UserService service = new UserServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try {
            String login = request.getParameter(CommandParameterHolder.PARAM_LOGIN.getName());
            String password = request.getParameter(CommandParameterHolder.PARAM_PASSWORD.getName());
            String firstName = request.getParameter(CommandParameterHolder.PARAM_FIRST_NAME.getName());
            String street = request.getParameter(CommandParameterHolder.PARAM_STREET.getName());
            int house = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_HOUSE.getName()));
            int apartment = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_APARTMENT.getName()));
            String phone = request.getParameter(CommandParameterHolder.PARAM_PHONE.getName());
            if(!service.checkLogin(login,password)) {
                if ((service.addNewUser(login, password, firstName, street, house, apartment, phone))) {
                    page = LOGIN_PATH;
                    request.getSession().removeAttribute(CommandParameterHolder.PARAM_SIGNUP_ERROR.getName());
                } else {
                    logger.log(Level.ERROR, "not valid input data");
                    request.getSession().setAttribute(CommandParameterHolder.PARAM_SIGNUP_ERROR.getName(), LOGIN_NOT_VALID_ERROR_MESSAGE);
                    page = SIGNUP_PATH;
                }
            }else {
                logger.log(Level.ERROR, "login already taken");
                request.getSession().setAttribute(CommandParameterHolder.PARAM_SIGNUP_ERROR.getName(), LOGIN_TAKEN_ERROR_MESSAGE);
                page = SIGNUP_PATH;
            }
        } catch (NumberFormatException e){
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_SIGNUP_ERROR.getName(), WRONG_INPUT_ERROR_MESSAGE);
            page = SIGNUP_PATH;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(CommandParameterHolder.PARAM_SIGNUP_ERROR.getName(), SIGNUP_ERROR_MESSAGE);
            page = SIGNUP_PATH;
        }
        return page;
    }
}
