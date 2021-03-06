package kz.kakimzhanova.delivery.command.impl;

import kz.kakimzhanova.delivery.command.Command;

import javax.servlet.http.HttpServletRequest;
/**
 * ForwardSignUpCommand class
 */
public class ForwardSignUpCommand implements Command {
    private static final String SIGNUP_PATH = "path.page.signup";

    /**
     * forwards to sign up page
     */
    @Override
    public String execute(HttpServletRequest request) {
        return SIGNUP_PATH;
    }
}
