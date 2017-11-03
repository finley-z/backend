package com.finley.common.usersession;

import com.finley.common.SystemConstant;
import com.finley.core.security.UserDetail;
import com.finley.module.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 郑远锋 on 2017/3/15.
 */
@Service
public class ServletUserSessionImp implements IUserSession {

    @Override
    public  User getUser(){
        User user=new User();
        UserDetail userDetail=getUserDetail();
        if(userDetail!=null){
            user.setUserType(userDetail.getUserType());
            user.setUserId(userDetail.getUserId());
            user.setUserName(userDetail.getUsername());
            user.setRoleId(userDetail.getRoleId());
        }
        return user;
    }

    @Override
    public boolean setUser(User user) {
        return false;
    }

    @Override
    public  Integer getUserId() {
        UserDetail userDetail=getUserDetail();
        if(userDetail!=null){
            return userDetail.getUserId();
        }
        return null ;
    }

    @Override
    public boolean invalidateSession() {
        return false;
    }

    @Override
    public User getUser(HttpServletRequest request) {
//        HttpSession session=request.getSession();
//        return (User) session.getAttribute(SystemConstant.USER_SESSION_KEY);
        return null;
    }

    @Override
    public boolean setUser(HttpServletRequest request, User user) {
        HttpSession session=request.getSession();
        session.setAttribute(SystemConstant.USER_SESSION_KEY,user);
        return true;
    }

    @Override
    public Integer getUserId(HttpServletRequest request) {
//        HttpSession session=request.getSession();
//        User user= (User) session.getAttribute(SystemConstant.USER_SESSION_KEY);
//        if(user!=null){
//            return user.getUserId();
//        }
        return null ;
    }

    @Override
    public boolean invalidateSession(HttpServletRequest request) {
//        HttpSession session=request.getSession();
//        session.invalidate();
        return false;
    }

    public UserDetail getUserDetail(){
        SecurityContext context=SecurityContextHolder.getContext();
        Authentication authentication=context.getAuthentication();
        UserDetail userDetails = (UserDetail)authentication.getPrincipal();
        return userDetails;
    }
}
