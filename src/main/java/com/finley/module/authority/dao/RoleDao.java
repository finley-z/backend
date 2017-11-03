package com.finley.module.authority.dao;

import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.entity.Role;
import com.finley.module.authority.entity.RoleAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 郑远锋 on 2017/3/1.
 */
public interface RoleDao{
	
	public List<Role> getRolesByCondition(Role Role);

	public int countRoles(Role Role);

	public int addRole(Role role);
	
	public int updateRole(Role role);

	public int deleteRoles(Integer[] roleIds);

	public Role getRole(@Param("roleId")Integer roleId);

	public List<Authority> getRoleAuthoritys(@Param("roleId")Integer roleId);

	public int grantAuths(List<RoleAuth> roleAuths);

	public int deleteAuths(@Param("roleId")Integer roleId);




}
