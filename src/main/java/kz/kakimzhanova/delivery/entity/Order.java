package kz.kakimzhanova.delivery.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order extends PersonalData {
    private static final long serialVersionUID = 2L;
    private int orderId;
    private OrderStatus status;
    private Date timestamp;
    private List<OrderedDish> orderList;
    private BigDecimal totalCost;

    public Order(String login, Timestamp timestamp, String firstName, String street, String house, int apartment, String phone, int orderId, List<OrderedDish> orderList, BigDecimal totalCost, OrderStatus status) {
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
        //return OrderStatus.values()[status];
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getTimestamp() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                status == order.status &&
                timestamp.equals(order.timestamp) &&
                Objects.equals(orderList, order.orderList) &&
                Objects.equals(totalCost, order.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, timestamp, orderList, totalCost);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", status=").append(status);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", orderList=").append(orderList);
        sb.append(", totalCost=").append(totalCost);
        sb.append('}');
        return sb.toString();
    }
}