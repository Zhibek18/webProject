package kz.kakimzhanova.delivery.entity;

import java.math.BigDecimal;

public class OrderedDish extends Dish{
    private static final long serialVersionUID = 3L;
    private int orderId;
    private int quantity;
    public OrderedDish(int orderId, String dishName, int quantity) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
    }

    public OrderedDish(int orderId, String dishName, String dishNameRu, String dishNameEn, String descriptionRu, String descriptionEn, BigDecimal price,  int quantity) {
        super(dishName, dishNameRu, dishNameEn, descriptionRu, descriptionEn, price);
        this.orderId = orderId;
        this.quantity = quantity;
    }
    public OrderedDish() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderedDish{");
        sb.append("orderId=").append(orderId);
        sb.append(", quantity=").append(quantity);
        sb.append(", dishName='").append(dishName).append('\'');
        sb.append(", dishNameRu='").append(dishNameRu).append('\'');
        sb.append(", dishNameEn='").append(dishNameEn).append('\'');
        sb.append(", descriptionRu='").append(descriptionRu).append('\'');
        sb.append(", descriptionEn='").append(descriptionEn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
