package kz.kakimzhanova.project.entity;

import java.math.BigDecimal;

public class Dish extends Entity {
    private String dishname;
    private BigDecimal price;

    public Dish(){
    }
    public Dish(String dishname, BigDecimal price) {
        this.dishname = dishname;
        this.price = price;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
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
        sb.append("dishname='").append(dishname).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
