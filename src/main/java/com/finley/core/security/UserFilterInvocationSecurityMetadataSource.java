package com.finley.core.security;

/**
 * @author zyf
 * @date 2017/3/1
 */

import com.finley.common.SystemConstant;
import com.finley.module.authority.entity.Authority;

import com.finley.module.authority.entity.RoleAuth;
import com.finley.module.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 该类是资源的访问权限的定义，实现资源和访问权限的对应关系
 * 该类的主要作用是在Spring Security的整个过滤链启动后，
 * 在容器启动的时候，程序就会进入到该类中的init()方法，init调用了loadResourceDefine()方法，
 * 该方法的主要目的是将数据库中的所有资源与权限读取到本地缓存中保存起来！
 * 类中的resourceMap就是保存的所有资源和权限的集合，URL为Key，权限作为Value！
 */

@Service
public class UserFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private AuthorityService authorityService;

    /**
     * resourceMap就是保存的所有资源和权限的集合，URL为Key，权限作为Value！
     */
    private static HashMap<String, Collection<ConfigAttribute>> resourceMap = null;


    /**
     * 在Spring容器对实例化该类时，指定init为初始化方法，从数据库中读取资源
     */
    @PostConstruct
    public void init() {
        loadResourceDefine();
    }

    /**
     * 程序启动的时候就加载所有资源信息
     * 初始化资源与权限的映射关系
     */
    private void loadResourceDefine() {
        Authority authority = new Authority();
        authority.setLevel(2);

        //获取系统所有需要访问权限的资源（url）
        List<Authority> resources = authorityService.findByCondition(authority);

        // 资源（url）为key，权限为value，一个资源可以由多个权限来访问。
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> auths = null;
        RoleAuth roleAuthCondition = new RoleAuth();
        if (resources != null && resources.size() > 0) {
            for (Authority auth : resources) {
                auths = new ArrayList<ConfigAttribute>();
                roleAuthCondition.setAuthId(auth.getId());
                //获取允许访问该资源的所有权限
                List<RoleAuth> roleAuths = authorityService.getRoleAuths(roleAuthCondition);
                auths = new ArrayList<ConfigAttribute>();
                for (int i = 0; i < roleAuths.size(); i++) {
                    auths.add(new SecurityConfig(SystemConstant.AUTH_PREFIX + roleAuths.get(i).getRoleId()));
                }
                resourceMap.put(auth.getUrl(), auths);
            }
        }
    }

    /**
     * 核心方法，获取url 所需要的权限（角色）
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        FilterInvocation filterInvocation = ((FilterInvocation) object);
        // 将参数转为url
        String url = ((FilterInvocation) object).getRequestUrl();
        //循环已有的角色配置对象 进行url匹配
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();

            //urlMatcher用来检查URL是否与资源定义匹配
            RequestMatcher urlMatcher = new AntPathRequestMatcher(resURL);
            if (urlMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public static HashMap<String, Collection<ConfigAttribute>> getResourceMap() {
        return resourceMap;
    }

    public static void setResourceMap(HashMap<String, Collection<ConfigAttribute>> resourceMap) {
        UserFilterInvocationSecurityMetadataSource.resourceMap = resourceMap;
    }

    public AuthorityService getAuthorityService() {
        return authorityService;
    }

    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }
}