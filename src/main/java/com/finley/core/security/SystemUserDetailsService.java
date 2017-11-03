package com.finley.core.security;

/**
 * @author zyf
 * @date 2017/3/1
 */

import com.finley.common.SystemConstant;
import com.finley.module.authority.dao.AuthorityDao;
import com.finley.module.authority.entity.RoleAuth;
import com.finley.module.user.dao.UserDao;
import com.finley.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 这个类主要是处理和验证用户登录信息，在用户触发登录事件后， spring security会带着用户名调用该类里面的loadUserByUsername(usrename)方法
 * 通过用户名查出用户信息，然后把数据库中查出的用户密码和刚刚用户输入的密码做比较，判断该用户是否合法，同时可验证用户的状态，决定是否通过用户登录。
 * 通过后，获取用户对应拥有的权限等信息，一起存入session中！
 */
@Service
public class SystemUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        User condition = new User();
        condition.setUserName(userName);
        if (userName == null||"".equals(userName)) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        //根据登录名获取数据库中对应的用户信息
        User user = userDao.findUser(condition);

        //判断用户是否存在
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        //判断用户是否禁用  0锁定、1正常
        boolean enabled = user.getStatus() == 1 ? true : false;

        UserDetail userDetail;

        if (enabled) {
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            RoleAuth roleAuth=new RoleAuth();
            roleAuth.setRoleId(user.getRoleId());
            //获取用户对应角色所拥有的权限
            List<RoleAuth> authorities = authorityDao.getRoleAuths(roleAuth);
            Iterator<RoleAuth> it = authorities.iterator();
            while (it.hasNext()) {
                auths.add(new SimpleGrantedAuthority(SystemConstant.AUTH_PREFIX+it.next().getAuthId()));
            }
            userDetail = new UserDetail(user.getUserName(), user.getPassword(), enabled, true, true, true, auths);
        } else {
            throw new LockedException("用户被锁定!");
        }
        userDetail.setUserType(user.getUserType());
        userDetail.setUserId(user.getUserId());
        userDetail.setRoleId(user.getRoleId());
        return userDetail;
    }

}
