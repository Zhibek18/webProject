package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ShowCheckCommand implements Command {
    private static final String CHECK_PATH = "path.page.check";

    @Override
    public String execute(HttpServletRequest request) {
        return CHECK_PATH;
    }
}
