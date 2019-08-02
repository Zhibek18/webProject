package kz.kakimzhanova.delivery.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order extends PersonalData {
    private int orderId;
    private int status;
    private Timestamp timestamp;
    private List<OrderedDish> orderList;
    private BigDecimal totalCost;

    public Order(String login, Timestamp timestamp, String firstName, String street, String house, int apartment, String phone, int orderId, List<OrderedDish> orderList, BigDecimal totalCost, int status) {
        super(login, firstName, street, house, apartment, phone);
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.orderList = orderList;
        this.totalCost = totalCost;
        this.status = status;
    }

    public Order(int orderId,String login, Timestamp timestamp) {
        super(login);
        this.orderId = orderId;
        this.timestamp = timestamp;
    }

    public Order() {
    }

    public Order(String login) {
        super(login);
    }

    public OrderStatus getStatus() {
        return OrderStatus.values()[status];
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<OrderedDish> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderedDish> orderList) {
        this.orderList = orderList;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", orderList=").append(orderList);
        sb.append(", totalCost=").append(totalCost);
        sb.append('}');
        return sb.toString();
    }
}