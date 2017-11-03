package com.finley.module.user.dao;

import com.finley.module.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zyf
 * @date 2017/2/16
 */
public interface UserDao{

    public int addUser(User user);

    public int updateUser(User user);

    public int modifyPassword(User user);

    public User findUser(User condition);

    public User findUserByCondition(User condition);

    public List<User> findUserByPage(User condition);

    public int countUsers(User condition);

    public User getUser(@Param("userId") Integer userId);

    public int isUserNameExist(@Param("userName") String userName);

}
