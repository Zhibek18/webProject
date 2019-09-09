package kz.kakimzhanova.delivery.dao;

import kz.kakimzhanova.delivery.entity.User;
import kz.kakimzhanova.delivery.exception.DaoException;

public interface UserDao extends BaseDao <String, User> {
    boolean updateUserPassword(String login, String password) throws DaoException;
}
