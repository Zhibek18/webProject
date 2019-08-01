package kz.kakimzhanova.delivery.entity;

public class User extends PersonalData{
    private String password;
    private boolean isAdmin;

    public User(String login, String password, String firstName, String street, String house, int apartment, String phone) {
        super(login, firstName, street, house, apartment, phone);
        this.password = password;
        this.isAdmin = false;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("password='").append(password).append('\'');
        sb.append(", isAdmin=").append(isAdmin);
        sb.append('}');
        return sb.toString();
    }
}
