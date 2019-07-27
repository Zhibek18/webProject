package kz.kakimzhanova.delivery.entity;

public class OrderedDish extends Entity{
    private int orderId;
    private String dishName;
    private int quantity;
    private float price; //TODO: check float or bigDecimal
    public OrderedDish(int orderId, String dishName, int quantity) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
    }

    public OrderedDish() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
        final StringBuilder stringBuilder = new StringBuilder("OrderedDish{");
        stringBuilder.append("  orderId=").append(orderId);
        stringBuilder.append(", dishName='").append(dishName).append('\'');
        stringBuilder.append(", quantity=").append(quantity);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
