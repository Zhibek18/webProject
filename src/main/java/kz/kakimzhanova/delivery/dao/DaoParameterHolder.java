package kz.kakimzhanova.delivery.dao;

public enum DaoParameterHolder {
    PARAM_LOGIN("login"),
    PARAM_PASSWORD("password"),
    PARAM_FIRST_NAME("first_name"),
    PARAM_STREET("street"),
    PARAM_HOUSE("house"),
    PARAM_APARTMENT("apartment"),
    PARAM_PHONE("phone"),
    PARAM_IS_ADMIN("is_admin"),
    PARAM_ORDER_ID("order_id"),
    PARAM_DISH_NAME("dish_name"),
    PARAM_QUANTITY("quantity"),
    PARAM_PRICE("price"),
    PARAM_TIMESTAMP("timestamp"),
    PARAM_TOTAL_COST("total_cost"),
    PARAM_DISH_NAME_RU("dish_name_ru"),
    PARAM_DISH_NAME_EN("dish_name_en"),
    PARAM_DESCRIPTION_RU("description_ru"),
    PARAM_DESCRIPTION_EN("description_en"),
    PARAM_STATUS("status");

    String name;

    DaoParameterHolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
