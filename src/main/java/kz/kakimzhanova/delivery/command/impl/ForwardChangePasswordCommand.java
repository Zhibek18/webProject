package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangePasswordCommand implements Command {
    private static final String CHANGE_PASSWORD_PATH = "path.page.changePassword";
    @Override
    public String execute(HttpServletRequest request) {
        return CHANGE_PASSWORD_PATH;
    }
}
