package kz.kakimzhanova.delivery.entity;

import java.sql.Timestamp;
import java.util.List;

public class Order extends Entity {
    private int orderId;
    private String login;
    private Timestamp timestamp;
    private String firstName;
    private String street;
    private int house;
    private int apartment;
    private String phone;
    private List<OrderedDish> orderList;
    private float totalCost;
    public List<OrderedDish> getOrderList() {
        return orderList;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void addDish(OrderedDish orderedDish){
        orderList.add(orderedDish);
    }

    public void setOrderList(List<OrderedDish> orderList) {
        this.orderList = orderList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
        final StringBuilder stringBuilder = new StringBuilder("Order{");
        stringBuilder.append("orderId=").append(orderId);
        stringBuilder.append(", login='").append(login).append('\'');
        stringBuilder.append(", timestamp=").append(timestamp);
        stringBuilder.append(", firstName='").append(firstName).append('\'');
        stringBuilder.append(", street='").append(street).append('\'');
        stringBuilder.append(", house=").append(house);
        stringBuilder.append(", apartment=").append(apartment);
        stringBuilder.append(", phone='").append(phone).append('\'');
        stringBuilder.append(", orderList=").append(orderList);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
