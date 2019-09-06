package kz.kakimzhanova.delivery.entity;

import java.util.Objects;

public class PersonalData extends Entity {
    private static final long serialVersionUID = 4L;
    private String login;
    private String firstName;
    private String street;
    private String house;
    private int apartment;
    private String phone;

    PersonalData(String login, String firstName, String street, String house, int apartment, String phone) {
        this.login = login;
        this.firstName = firstName;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.phone = phone;
    }

    PersonalData() {
    }

    PersonalData(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalData)) return false;
        PersonalData that = (PersonalData) o;
        return apartment == that.apartment &&
                login.equals(that.login) &&
                firstName.equals(that.firstName) &&
                street.equals(that.street) &&
                house.equals(that.house) &&
                phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, street, house, apartment, phone);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonalData{");
        sb.append("login='").append(login).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", house='").append(house).append('\'');
        sb.append(", apartment=").append(apartment);
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
