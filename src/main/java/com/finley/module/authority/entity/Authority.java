package com.finley.module.authority.entity;

import com.finley.module.common.entity.BaseEntity;

import java.util.List;

/**
 * 用户权限
 * @author 郑远锋
 *
 */
public class Authority extends BaseEntity{

	//ROLE_"为前缀的代码
	private String  code;

	//权限名称
    private String authorityName;
	
	//父权限
	private Integer parentId;

	private List<Authority> subAuthority;
	
	//资源路径
	private String url;

	//权限树形深度
    private Integer level;
;
	//权限是否可用
	private Integer status;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		if(this.parentId!=null){
			return 2;
		}else{
			return 1;
		}
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Authority> getSubAuthority() {
		return subAuthority;
	}

	public void setSubAuthority(List<Authority> subAuthority) {
		this.subAuthority = subAuthority;
	}
}