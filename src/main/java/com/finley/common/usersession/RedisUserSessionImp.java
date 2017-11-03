package com.finley.common.usersession;

import com.finley.common.SystemConstant;
import com.finley.common.cache.ApplicationCache;
import com.finley.module.user.entity.User;
import com.finley.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 郑远锋 on 2017/3/15.
 */
@Service
public class RedisUserSessionImp implements IUserSession {
    @Autowired
    @Qualifier("redisApplicationCacheImp")
    private ApplicationCache _applicationCache;

    /**
     * 静态化实例
     */
    private static ApplicationCache applicationCache;

    /**
     * 类型完成实例化后，给类型注入静态值
     */
    public @PostConstruct  void initTemplate() {
        applicationCache = this._applicationCache;
    }

    @Override
    public  User getUser(){
        String sessionId= CookieUtil.get(SystemConstant.USER_SESSION_KEY);
        try{
            if(sessionId==null||"".equals(sessionId)){
                return null;
            }
            return (User) applicationCache.getObject(sessionId);
        }
        catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean setUser(User user) {
        try{
            applicationCache.addObject(SystemConstant.USER_SESSION_KEY,user,SystemConstant.USER_SESSION_TIMEOUT);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public  Integer getUserId() {
        String sessionId= CookieUtil.get(SystemConstant.USER_SESSION_KEY);
        try{
            if(sessionId==null||"".equals(sessionId)){
                return null;
            }
            User user=(User)  applicationCache.getObject(sessionId);
            return user.getUserId();
        }  catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean invalidateSession() {
        String sessionId= CookieUtil.get(SystemConstant.USER_SESSION_KEY);
        try{
             applicationCache.removeObject(sessionId);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean setUser(HttpServletRequest request, User user) {
        return false;
    }

    @Override
    public Integer getUserId(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean invalidateSession(HttpServletRequest request) {
        return false;
    }
}
