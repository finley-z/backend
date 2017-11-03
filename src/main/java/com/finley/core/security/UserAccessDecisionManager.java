package com.finley.core.security;

/**
 * @author zyf
 * @date 2017/3/1
 */

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * 访问决策器，对用户的请求访问进行鉴权。有权放行，无权做出相应的反馈
 */
@Service
public class UserAccessDecisionManager implements AccessDecisionManager {


    /**
     * 鉴权核心方法，通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication   当前正在请求受包含对象的Authentication
     * @param object           受保护对象 （URL），其可以是一个MethodInvocation、JoinPoint或FilterInvocation。
     * @param configAttributes 与正在请求的受保护对象相关联的配置属性
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        Iterator<ConfigAttribute> it = configAttributes.iterator();
        while (it.hasNext()) {
            ConfigAttribute ca = it.next();
            String needRole = ((SecurityConfig) ca).getAttribute();
            //grantedAuthority 为用户所被赋予的权限，needRole为访问相应的资源应具有的权限
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needRole.trim().equals(grantedAuthority.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("Access Denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}