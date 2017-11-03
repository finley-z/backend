package com.finley.module.user.service;

import com.finley.module.user.dao.UserDao;
import com.finley.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zyf
 * @date 2017/3/9
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     *添加用户
     * @param user
     * @return
     */
    public boolean addUser(User user){
        return userDao.addUser(user)>0?true:false;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean register(User user){
        return userDao.addUser(user)>0?true:false;
    }


    /**
     * 查找用户
     * @param user
     * @return
     */
    public User findUserByCondition(User user){
        return userDao.findUserByCondition(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user){
        return userDao.updateUser(user)>0?true:false;
    }


    /**
     * 修改密码
     * @param user
     * @return
     */
    public boolean modifyPassword(User user){
        return userDao.modifyPassword(user)>0?true:false;
    }

    public User getUser(Integer userId){
        return userDao.getUser(userId);
    }

    public boolean isUserNameExist(String userName){
        return userDao.isUserNameExist(userName)>0?true:false;
    }
}
