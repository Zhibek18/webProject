package kz.kakimzhanova.project.service;

public class UserService {
    public boolean checkLogin(String login, String password){
        return (login.equals(password));
    }
}
