package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangeAddressCommand implements Command {
    private static final String CHANGE_ADDRESS_PATH = "path.page.changeAddress";
    @Override
    public String execute(HttpServletRequest request) {
        return CHANGE_ADDRESS_PATH;
    }
}
