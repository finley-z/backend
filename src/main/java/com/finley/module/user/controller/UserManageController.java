package com.finley.module.user.controller;

import com.finley.common.SystemConstant;
import com.finley.core.pagination.PageVo;
import com.finley.core.respone.ResultVo;
import com.finley.enums.UserStatusEnum;
import com.finley.module.user.entity.User;
import com.finley.module.user.service.UserManageService;
import com.finley.module.user.service.UserService;
import com.finley.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zyf
 * @date 2017/3/13
 */

@Controller
@RequestMapping(value = "/user")
public class UserManageController {
    @Autowired
    UserManageService userManageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "userManage")
    public String userManage() {
        return "user/userManage";
    }

    /**
     *  保存用户
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveUser")
    public ResultVo saveUser(User user) {
        ResultVo resultVo = new ResultVo(true);
        try {
            //判断是新增用户还是更新用户
            if (user.getUserId() == null) {
                //设置用户类型
                user.setUserType(1);
                //对密码进行加密
                user.setPassword(EncryptUtil.encrypt(SystemConstant.DEFAULT_USER_PASSWORD));
                userService.addUser(user);
            } else {
                userService.updateUser(user);
            }
        } catch (Exception e) {
            resultVo.setStatus(false);
            e.printStackTrace();
        }
        return resultVo;
    }

    /**
     *查找系统管理员用户类表
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findSysUsers")
    public PageVo<User> findSysUsers(User user) {
        PageVo<User> pageVo = new PageVo<User>();
        pageVo.setData(userManageService.findUsers(user));
        int count = userManageService.countUsers(user);
        pageVo.setRecordsTotal(count);
        pageVo.setRecordsFiltered(count);
        return pageVo;
    }

    /**
     *更改用户角色
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyRole")
    public ResultVo modifyRole(User user) {
        ResultVo resultVo = new ResultVo(true);
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            resultVo.setStatus(false);
            e.printStackTrace();
        }
        return resultVo;
    }

    /**
     *重置用户密码
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "resetPassword")
    public ResultVo resetPassword(User user) {
        ResultVo resultVo = new ResultVo(true);
        try {
            user.setPassword(EncryptUtil.encrypt(SystemConstant.DEFAULT_USER_PASSWORD));
            userService.modifyPassword(user);
        } catch (Exception e) {
            resultVo.setStatus(false);
            e.printStackTrace();
        }
        return resultVo;
    }

    /**
     *锁定用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "lockUser")
    public ResultVo lockUser(User user) {
        ResultVo resultVo = new ResultVo(true);
        try {
            user.setStatus(UserStatusEnum.UNABLED.getStatus());
            userService.updateUser(user);
        } catch (Exception e) {
            resultVo.setStatus(false);
            e.printStackTrace();
        }
        return resultVo;
    }

    /**
     * 解锁用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "enabledUser")
    public ResultVo enabledUser(User user) {
        ResultVo resultVo = new ResultVo(true);
        try {
            user.setStatus(UserStatusEnum.ENABLED.getStatus());
            userService.updateUser(user);
        } catch (Exception e) {
            resultVo.setStatus(false);
            e.printStackTrace();
        }
        return resultVo;
    }

    /**
     *获取用户信息
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUser")
    public User getUser(Integer userId) {
        return userService.getUser(userId);
    }

    /**判断用户名是否已存在
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "isUserNameExist")
    public Map<String,Boolean> isUserNameExist(@RequestParam String userName) {
        Map<String,Boolean> res=new HashMap<String, Boolean>();
        res.put("valid",!userService.isUserNameExist(userName));
        return res;
    }
}

