package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean checkLogin(String login, String password) throws ServiceException;
    boolean isAdmin(String login) throws ServiceException;
    boolean addNewUser(String login, String password, String firstName, String street, int house, int apartment, String phone) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
    boolean deleteUser(String login) throws ServiceException;
    boolean changePassword(String login, String oldPassword, String newPassword) throws ServiceException;
    boolean changeAddress(String login, String street, int house, int apartment) throws ServiceException;
}
