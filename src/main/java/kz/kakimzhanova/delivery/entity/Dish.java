package kz.kakimzhanova.delivery.entity;

import java.math.BigDecimal;

public class Dish extends Entity {
    protected String dishName;
    protected String dishNameRu;
    protected String dishNameEn;
    protected String descriptionRu;
    protected String descriptionEn;

    public Dish(String dishName, String dishNameRu, String dishNameEn, String descriptionRu, String descriptionEn, BigDecimal price) {
        this.dishName = dishName;
        this.dishNameRu = dishNameRu;
        this.dishNameEn = dishNameEn;
        this.descriptionRu = descriptionRu;
        this.descriptionEn = descriptionEn;
        this.price = price;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    private BigDecimal price;

    public Dish(){
    }

    public String getDishNameRu() {
        return dishNameRu;
    }

    public void setDishNameRu(String dishNameRu) {
        this.dishNameRu = dishNameRu;
    }

    public String getDishNameEn() {
        return dishNameEn;
    }

    public void setDishNameEn(String dishNameEn) {
        this.dishNameEn = dishNameEn;
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
