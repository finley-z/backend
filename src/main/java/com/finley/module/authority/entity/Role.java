package com.finley.module.authority.entity;

import com.finley.module.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public class Role extends BaseEntity{
	private Integer roleId;         //角色ID
	private String roleName;        //角色名称
	private String roleDesc;        //角色描述
	private Integer status;         //角色状态
	private List<Authority> auths;	 //角色拥有的权限

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Authority> getAuths() {
		return auths;
	}

	public void setAuths(List<Authority> auths) {
		this.auths = auths;
	}
}
