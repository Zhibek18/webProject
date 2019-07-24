package kz.kakimzhanova.project.entity;

public class OrderedDish extends Entity{
    private int orderId;
    private String dishName;
    private int quantity;

    public OrderedDish(int orderId, String dishName, int quantity) {
        this.orderId = orderId;
        this.dishName = dishName;
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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
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
        sb.append("  orderId=").append(orderId);
        sb.append(", dishName='").append(dishName).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
