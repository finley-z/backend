package com.finley.common.usersession;

import com.finley.module.user.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ֣Զ�� on 2017/5/11.
 */
public interface IUserSession {

    public User getUser();

    public boolean setUser(User user);

    public Integer getUserId();

    public boolean invalidateSession();

    public User getUser(HttpServletRequest request);

    public boolean setUser(HttpServletRequest request,User user);

    public Integer getUserId(HttpServletRequest request);

    public boolean invalidateSession(HttpServletRequest request);

}

