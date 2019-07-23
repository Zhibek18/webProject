package kz.kakimzhanova.project.entity;

import java.sql.Timestamp;

public class Order extends Entity {
    private int orderId;
    private String login;
    private Timestamp timestamp;

    public Order(int orderId, String login, Timestamp timestamp) {
        this.orderId = orderId;
        this.login = login;
        this.timestamp = timestamp;
    }

    public Order(String login) {
        this.login = login;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", login='").append(login).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
