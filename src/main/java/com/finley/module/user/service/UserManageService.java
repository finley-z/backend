package com.finley.module.user.service;

import com.finley.module.user.dao.UserDao;
import com.finley.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zyf
 * @date 2017/3/13
 */

@Service
public class UserManageService {
    @Autowired
    UserDao userDao;

    /**
     * 查找用户列表
     * @param user
     * @return
     */
    public List<User> findUsers(User user){
        return userDao.findUserByPage(user);
    }

    /**
     *统计用户数量
     * @param user
     * @return
     */
    public int countUsers(User user){
        return userDao.countUsers(user);
    }
}
