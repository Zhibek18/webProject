package kz.kakimzhanova.delivery.entity;

import java.util.Objects;

public class User extends PersonalData{
    private static final long serialVersionUID = 5L;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password, isAdmin);
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
