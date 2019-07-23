package kz.kakimzhanova.project.entity;

public class User extends Entity{
    private String login;
    private String password;
    private String firstname;
    private String street;
    private int house;
    private int apartment;
    private String phone;

    public User(String login, String password, String firstname, String street,int house, int apartment, String phone ) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.phone = phone;
    }

    public User(){

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", firstname='").append(firstname).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", house=").append(house);
        sb.append(", apartment=").append(apartment);
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
