package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.DaoException;

public interface UserDao extends BaseDao <String, User> {
    boolean updateUserPassword(String login, String password) throws DaoException;
    User updateUser(String login,String firstName, String street, String house, int apartment, String phone) throws DaoException;
}
