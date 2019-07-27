package kz.kakimzhanova.delivery.entity;

public class User extends Entity{
    private String login;
    private String password;
    private String firstName;
    private String street;
    private int house;
    private int apartment;
    private String phone;
    private boolean isAdmin;

    public User(String login, String password, String firstName, String street,int house, int apartment, String phone ) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.phone = phone;
    }

    public User(){

    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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
        final StringBuilder stringBuilder = new StringBuilder("User{");
        stringBuilder.append("login='").append(login).append('\'');
        stringBuilder.append(", password='").append(password).append('\'');
        stringBuilder.append(", firstName='").append(firstName).append('\'');
        stringBuilder.append(", street='").append(street).append('\'');
        stringBuilder.append(", house=").append(house);
        stringBuilder.append(", apartment=").append(apartment);
        stringBuilder.append(", phone='").append(phone).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
