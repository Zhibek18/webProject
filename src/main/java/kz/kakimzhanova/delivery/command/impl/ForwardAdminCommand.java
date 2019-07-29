package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandParameterHolder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ForwardAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADMIN_PATH = "path.page.admin";
    private static final String INDEX_PATH = "path.page.index";
    @Override
    public String execute(HttpServletRequest request) {
        if ((Boolean) request.getSession().getAttribute(CommandParameterHolder.PARAM_IS_ADMIN.getName())) {
            return ADMIN_PATH;
        } else {
            return INDEX_PATH;
        }
    }
}
