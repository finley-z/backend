package com.finley.common.usersession;

import com.finley.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zyf
 * @date 2017/5/12
 */
@Service
public class UserSession {

    @Autowired
    @Qualifier("servletUserSessionImp")
    private IUserSession _session;

    /**
     * 静态化实例
     */
    private static IUserSession session;

    /**
     * 类型完成实例化后，将实例赋给静态实例
     */
    public  @PostConstruct void initTemplate() {
        session = this._session;
    }

    public static User getUser() {
        return session.getUser();
    }

    public static boolean setUser(User user) {
        return session.setUser(user);
    }

    public static Integer getUserId() {
        return session.getUserId();
    }



    public static Integer getUserType(){
        User user=session.getUser();
        if(user!=null){
            return user.getUserType();
        }
        return null;
    }



    public static Integer getRoleId(){
        User user=session.getUser();
        if(user!=null){
            return user.getRoleId();
        }
        return null;
    }


    public static void invalidateSession(){
         session.invalidateSession();
    }
    public static User getUser(HttpServletRequest request) {
        return session.getUser(request);
    }

    public static boolean setUser(HttpServletRequest request,User user) {
        return session.setUser(request,user);
    }

    public static Integer getUserId(HttpServletRequest request) {
        return session.getUserId(request);
    }

    public static void invalidateSession(HttpServletRequest request){
        session.invalidateSession(request);
    }
}
