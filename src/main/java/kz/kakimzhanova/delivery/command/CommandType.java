package kz.kakimzhanova.delivery.command;

import kz.kakimzhanova.delivery.command.impl.*;

/**
 * enum CommandType contains all command names with appropriate Command objects
 */
public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignupCommand()),
    FORWARD_SIGNUP(new ForwardSignUpCommand()),
    DELETE_USER(new DeleteUserCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    FORWARD_UPDATE_USER(new ForwardUpdateUserCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    SHOW_MENU(new ShowMenuCommand()),
    ADD_DISH(new AddDishToOrderCommand()),
    SHOW_ORDER(new ShowOrderCommand()),
    DELETE_ORDERED_DISH(new DeleteOrderedDishCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    CHANGE_DISH_QUANTITY(new ChangeDishQuantityCommand()),
    USER_CONFIRM_ORDER(new UserConfirmOrderCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    FORWARD_MAIN(new ForwardMainCommand()),
    FORWARD_ADMIN(new ForwardAdminCommand()),
    EDIT_DISH(new EditDishCommand()),
    FORWARD_EDIT_MENU(new ForwardEditMenuCommand()),
    DELETE_DISH_FROM_MENU(new DeleteDishFromMenuCommand()),
    ADD_DISH_TO_MENU(new AddDishToMenuCommand()),
    ADMIN_CONFIRM_ORDER(new AdminConfirmOrderCommand()),
    CANCEL_ORDER(new CancelOrderCommand());
    private Command command;

    /**
     * Constructor
     * @param command
     */
    CommandType(Command command) {
        this.command = command;
    }

    /**
     *
     * @return appropriate Command object
     */
    public Command getCommand() {
        return command;
    }
}
