package com.finley.module.authority.dao;

import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.entity.Role;
import com.finley.module.authority.entity.RoleAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 郑远锋 on 2017/3/1.
 */
public interface AuthorityDao {
    public List<Authority> getByCondition(Authority authority);

    public Authority  getAuthorityByCondition(@Param("id") Integer authId);

    public int addAuthority(Authority authority);

    public int updateAuthority(Authority authority);

    public int deleteAuths(Integer[] authIds);

    public int addRole(Role role);

    public List<Authority> getAuthorityMap(Authority authority);

    public List<RoleAuth> getRoleAuths(RoleAuth roleAuth);


}
