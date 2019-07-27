package kz.kakimzhanova.delivery.entity;

import java.math.BigDecimal;

public class Dish extends Entity {
    private String dishName;
    private BigDecimal price;

    public Dish(){
    }
    public Dish(String dishName, BigDecimal price) {
        this.dishName = dishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dish{");
        sb.append("dishName='").append(dishName).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
