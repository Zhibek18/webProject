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

    public String getDish() {
        return dishname;
    }

    public void setDish(String dishname) {
        this.dishname = dishname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
