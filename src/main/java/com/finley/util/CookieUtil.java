package com.finley.util;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author finley
 * @date 2017/2/18 19:18
 */
public class CookieUtil {
    /**
     * 设置cookie
     *
     * @param
     * @param name   cookie名字
     * @param value  cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void addCookie(String name, String value, int maxAge) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 设置cookie
     *
     * @param responsCopy 相应对象
     * @param name        cookie名字
     * @param value       cookie值
     * @param maxAge      cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse responsCopy, String name, String value, int maxAge) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }


    /**
     * 根据key获取cookie的值
     *
     * @param
     * @return
     */
    public static String get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    /**
     * 根据key获取cookie的值
     *
     * @param
     * @return
     */
    public static String get(String key, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 根据key获取cookie
     *
     * @param
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookie(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Cookie> cookieMap = getCookieMap();
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 根据key获取cookie
     *
     * @param
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookie(String name, HttpServletRequest request) {
        Map<String, Cookie> cookieMap = getCookieMap();
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }


    /**
     * 将cookie信息封装到Map对象返回
     *
     * @param
     * @return
     */
    public static Map<String, Cookie> getCookieMap() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 将cookie信息封装到Map对象返回
     *
     * @param
     * @return
     */
    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
