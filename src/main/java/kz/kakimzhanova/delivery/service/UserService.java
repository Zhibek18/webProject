package kz.kakimzhanova.delivery.service;

import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.ServiceException;

import javax.xml.ws.Service;
import java.util.List;

public interface UserService {
    boolean checkLogin(String login, String password) throws ServiceException;
    boolean isAdmin(String login) throws ServiceException;
    boolean addNewUser(User user) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
    boolean deleteUser(String login) throws ServiceException;
    boolean changePassword(String login, String oldPassword, String newPassword) throws ServiceException;
    User updateUser( User user) throws ServiceException;
    User findById(String login) throws ServiceException;
}
