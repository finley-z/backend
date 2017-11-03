package com.finley.module.authority.service;


import com.finley.module.authority.dao.AuthorityDao;
import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.entity.RoleAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class AuthorityService {
	@Autowired
	public AuthorityDao authorityDao;

	/**
	 * 添加权限
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addAuthority(Authority entity){
		return authorityDao.addAuthority(entity)>0?true:false;
	}

	/**
	 * 修改权限
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateAuthority(Authority entity){
		return authorityDao.updateAuthority(entity)>0?true:false;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delteAuths(Integer[] authIds){
		return authorityDao.deleteAuths(authIds)>0?true:false;
	}

	/***
	 *条件查询权限
	 * @param entity
	 * @return
	 */
	public List<Authority> findByCondition(Authority entity){
		return authorityDao.getByCondition(entity);
	}


	/***
	 * 获取权限关系图
	 * @param entity
	 * @return
	 */
	public List<Authority> getAuthorityMap(Authority entity){
		entity.setLevel(1);
		List<Authority> authsMap=authorityDao.getByCondition(entity);
		Iterator<Authority> iterator=authsMap.iterator();
		while(iterator.hasNext()){
			Authority parent=iterator.next();
			entity.setLevel(2);
			entity.setParentId(parent.getId());
			parent.setSubAuthority(authorityDao.getByCondition(entity));
		}
		return authsMap;
	}

	/***
	 * 获取RoleAuths 集合
	 * @param roleAuth
	 * @return
	 */
	public List<RoleAuth> getRoleAuths(RoleAuth roleAuth){
		return authorityDao.getRoleAuths(roleAuth);
	}

	public Authority getAuthority(Integer authId){
		return authorityDao.getAuthorityByCondition(authId);
	}
}
