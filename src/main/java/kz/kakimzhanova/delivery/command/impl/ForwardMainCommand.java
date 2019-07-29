package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ForwardMainCommand implements Command {
    private static final String MAIN_PATH = "path.page.main";
    @Override
    public String execute(HttpServletRequest request) {
        return MAIN_PATH;
    }
}
