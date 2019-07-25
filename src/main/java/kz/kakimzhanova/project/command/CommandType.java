package kz.kakimzhanova.project.command;

import kz.kakimzhanova.project.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignupCommand()),
    SHOWUSERS(new ShowUsersCommand()),
    FORWARDSIGNUP(new ForwardSignUpCommand()),
    FORWARDCHANGEPASSWORD(new ForwardChangePasswordCommand()),
    DELETEUSER(new DeleteUserCommand()),
    CHANGEPASSWORD(new ChangePasswordCommand()),
    FORWARDCHANGEADDRESS(new ForwardChangeAddressCommand()),
    CHANGEADDRESS(new ChangeAddressCommand()),
    SHOWMENU(new ShowMenuCommand()),
    ADDDISH(new AddDishCommand()),
    SHOWORDER(new ShowOrderCommand()),
    DELETEORDEREDDISH(new DeleteOrderedDishCommand()),
    DELETEORDER(new DeleteOrderCommand()),
    SHOWORDERS(new ShowOrdersCommand()),
    CHANGEDISHQUANTITY(new ChangeDishQuantityCommand()),
    UPDATEORDERTOTALCOST(new UpdateOrderTotalCostCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
