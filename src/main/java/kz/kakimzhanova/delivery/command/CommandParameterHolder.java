package kz.kakimzhanova.delivery.command;

public enum CommandParameterHolder {
    PARAM_LOGIN("login"),
    PARAM_PASSWORD("password"),
    PARAM_FIRST_NAME("firstName"),
    PARAM_STREET("street"),
    PARAM_HOUSE("house"),
    PARAM_APARTMENT("apartment"),
    PARAM_PHONE("phone"),
    PARAM_IS_ADMIN("isAdmin"),
    PARAM_USER_NAMES("userNames"),
    PARAM_ORDER("order"),
    PARAM_ORDER_ID("orderId"),
    PARAM_TOTAL_COST("totalCost"),
    PARAM_CONFIRMED_ORDER("confirmedOrder"),
    PARAM_ORDERS("orders"),
    PARAM_MENU("menu"),
    PARAM_DISH_NAME("dishName"),
    PARAM_OLD_PASSWORD("oldPassword"),
    PARAM_NEW_PASSWORD("newPassword"),
    PARAM_LANGUAGE("language"),
    PARAM_PAGE("page"),
    PARAM_DISH_QUANTITY("quantity"),
    PARAM_ADDED("added"),
    PARAM_NOT_ADDED("notAdded"),
    PARAM_CHANGE_ADDRESS_ERROR("changeAddressError"),
    PARAM_CHANGE_QUANTITY_ERROR("changeQuantityError"),
    PARAM_UPDATE_USER_ERROR("updateUserError"),
    PARAM_DELETE_ORDER_ERROR("deleteOrderError"),
    PARAM_DELETE_DISH_ERROR("deleteDishOrder"),
    PARAM_DELETE_USER_ERROR("deleteUserError"),
    PARAM_LOGIN_ERROR("errorLogin"),
    PARAM_SHOW_MENU_ERROR("showMenuError"),
    PARAM_ORDER_ERROR("orderError"),
    PARAM_SHOW_ORDERS_ERROR("showOrdersError"),
    PARAM_SHOW_USERS_ERROR("showUsersError"),
    PARAM_SIGNUP_ERROR("errorSignUpMessage"),
    PARAM_ORDER_CONFIRM_ERROR("orderConfirmError"),
    PARAM_STATUS_NOT_ADDED("addingStatus.notAdded"),
    PARAM_STATUS_ADDED("addingStatus.added"),
    PARAM_USER("user"),
    PARAM_UPDATE_PASSWORD_ERROR("updatePasswordError"),
    PARAM_EDIT_MENU_ERROR("editMenuError"),
    PARAM_DISH_PRICE("dishPrice"),
    PARAM_DISH_NAME_RU("dishNameRu"),
    PARAM_DISH_NAME_EN("dishNameEn"),
    PARAM_DESCRIPTION_RU("descriptionRu"),
    PARAM_DESCRIPTION_EN("descriptionEn");
    private String name;

    CommandParameterHolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
