package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;
/**
 * ForwardUpdateUserCommand class
 */
public class ForwardUpdateUserCommand implements Command {
    private static final String UPDATE_USER_PATH = "path.page.updateUser";

    /**
     * execute method forwards to updateUser page
     */
    @Override
    public String execute(HttpServletRequest request) {
        return UPDATE_USER_PATH;
    }
}
