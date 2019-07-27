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

public class ChangeAddressCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String CHANGE_ADDRESS_ERROR_MESSAGE = "changeAddress.error";
    private static final String WRONG_INPUT_MESSAGE = "wrongInput.error";
    private static final String MAIN_PATH = "path.page.main";
    private static final String CHANGE_ADDRESS_PATH = "path.page.changeAddress";
    private UserService service = new UserServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getSession().getAttribute(CommandParameterHolder.PARAM_LOGIN.getName()).toString();
        String street = request.getParameter(CommandParameterHolder.PARAM_STREET.getName());
        try {
            int house = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_HOUSE.getName()));
            int apartment = Integer.parseInt(request.getParameter(CommandParameterHolder.PARAM_APARTMENT.getName()));
            if (service.changeAddress(login, street, house, apartment)) {
                page = MAIN_PATH;
            } else {
                logger.log(Level.WARN, "changeAddress returned false");
                request.setAttribute(CommandParameterHolder.PARAM_CHANGE_ADDRESS_ERROR.getName(), CHANGE_ADDRESS_ERROR_MESSAGE);
                page = CHANGE_ADDRESS_PATH;
            }
        }catch (NumberFormatException e){
            logger.log(Level.ERROR, e);
            request.setAttribute(CommandParameterHolder.PARAM_CHANGE_ADDRESS_ERROR.getName(), WRONG_INPUT_MESSAGE);
            page = CHANGE_ADDRESS_PATH;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(CommandParameterHolder.PARAM_CHANGE_ADDRESS_ERROR.getName(), CHANGE_ADDRESS_ERROR_MESSAGE);
            page = CHANGE_ADDRESS_PATH;
        }
        return page;
    }
}
