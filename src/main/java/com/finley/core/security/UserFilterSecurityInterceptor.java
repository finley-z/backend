package com.finley.core.security;

/**
 * @author zyf
 * @date 2017/3/1
 */

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class UserFilterSecurityInterceptor extends
        AbstractSecurityInterceptor implements Filter {

    // 注入资源数据定义器
    @Resource
    @Qualifier("userFilterInvocationSecurityMetadataSource")
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    // 注入访问决策器
    @Override
    @Resource
    @Qualifier(value = "userAccessDecisionManager")
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    // 注入认证管理器
    @Resource
    @Qualifier("authenticationManager")
    @Override
    public void setAuthenticationManager(AuthenticationManager newManager) {
        super.setAuthenticationManager(newManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session=httpRequest.getSession();
        //限制匿名用户登录,避免通过在地址栏输入有效的url访问系统
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") == null) {
            String path = httpRequest.getContextPath();
            String basePath = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort() + path;
            httpRequest.getRequestDispatcher("/login.html").forward(httpRequest,httpResponse);
            return;
        }
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        invoke(filterInvocation);
    }

    /**
     * @param filterInvocation
     * @throws ServletException
     * @throws IOException
     */
    private void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        InterceptorStatusToken token = null;
        try {
            token = super.beforeInvocation(filterInvocation);
        } catch (Exception e) {
            e.printStackTrace();
            // 用户登录情况下 系统中存在用户访问的资源url和权限，但是当前用户的角色中没有这个权限 则提示跳转用户无权访问的页面
            if (e instanceof AccessDeniedException) {
                HttpServletRequest httpRequest = filterInvocation.getRequest();
                HttpServletResponse httpResponse = filterInvocation.getResponse();
                String path = httpRequest.getContextPath();
                String basePath = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort() + path;
                RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/common/page-403.jsp");
                dispatcher.forward(httpRequest, httpResponse);
            }
            return;
        }

        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public void destroy() {

    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(
            FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

}