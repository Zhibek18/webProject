package kz.kakimzhanova.project.dao;

import kz.kakimzhanova.project.entity.User;
import kz.kakimzhanova.project.exception.DaoException;

import java.util.List;

public interface UserDao extends BaseDao <String, User> {
    boolean updateUserPassword(String login, String password) throws DaoException;
}
