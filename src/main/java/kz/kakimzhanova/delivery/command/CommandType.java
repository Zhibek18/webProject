package kz.kakimzhanova.delivery.command;

import kz.kakimzhanova.delivery.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignupCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    FORWARD_SIGNUP(new ForwardSignUpCommand()),
    FORWARD_CHANGE_PASSWORD(new ForwardChangePasswordCommand()),
    DELETE_USER(new DeleteUserCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    FORWARD_CHANGE_ADDRESS(new ForwardChangeAddressCommand()),
    CHANGE_ADDRESS(new ChangeAddressCommand()),
    SHOW_MENU(new ShowMenuCommand()),
    ADD_DISH(new AddDishCommand()),
    SHOW_ORDER(new ShowOrderCommand()),
    DELETE_ORDERED_DISH(new DeleteOrderedDishCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    SHOW_ORDERS(new ShowOrdersCommand()),
    CHANGE_DISH_QUANTITY(new ChangeDishQuantityCommand()),
    UPDATE_ORDER_TOTAL_COST(new UpdateOrderTotalCostCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    FORWARD_MAIN(new ForwardMainCommand()),
    FORWARD_ADMIN(new ForwardAdminCommand());
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
