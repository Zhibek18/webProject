package kz.kakimzhanova.project.command;

import kz.kakimzhanova.project.command.impl.LoginCommand;
import kz.kakimzhanova.project.command.impl.LogoutCommand;
import kz.kakimzhanova.project.command.impl.SignupCommand;

public enum CommandType {
    LOGIN(new LoginCommand()), LOGOUT(new LogoutCommand()), SIGNUP(new SignupCommand());
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
