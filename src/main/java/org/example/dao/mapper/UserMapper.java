package org.example.dao.mapper;

import org.example.dao.entity.User;

import java.util.List;

/**
 * Created by Super on 2023/9/17 23:08
 */
public interface UserMapper {

    /**
     * find user by id
     *
     * @param id    userId
     * @return  {@link User}
     */
    User queryUserById(int id);

    /**
     * get all users
     *
     * @return {@link List}
     */
    List<User> queryUserAll();

    /**
     * add user
     *
     * @param user {@link User}
     */
    void insertUser(User user);

    /**
     * update user's information
     *
     * @param user {@link User}
     */
    void updateUser(User user);

    /**
     *  delete a user by id
     *
     * @param id userId
     */
    void deleteUserById(int id);

}
