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
    FORWARD_UPDATE_USER(new ForwardUpdateUserCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    SHOW_MENU(new ShowMenuCommand()),
    ADD_DISH(new AddDishToOrderCommand()),
    SHOW_ORDER(new ShowOrderCommand()),
    DELETE_ORDERED_DISH(new DeleteOrderedDishCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    SHOW_ORDERS(new ShowOrdersCommand()),
    CHANGE_DISH_QUANTITY(new ChangeDishQuantityCommand()),
    UPDATE_ORDER_TOTAL_COST(new UpdateOrderTotalCostCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    FORWARD_MAIN(new ForwardMainCommand()),
    FORWARD_ADMIN(new ForwardAdminCommand()),
    EDIT_DISH(new EditDishCommand()),
    FORWARD_EDIT_MENU(new ForwardEditMenuCommand()),
    DELETE_DISH_FROM_MENU(new DeleteDishFromMenuCommand()),
    ADD_DISH_TO_MENU(new AddDishToMenuCommand()),
    CONFIRM_ORDER(new ConfirmOrderCommand());
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
