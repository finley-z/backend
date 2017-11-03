package com.finley.module.authority.service;


import com.finley.module.authority.dao.RoleDao;
import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.entity.Role;
import com.finley.module.authority.entity.RoleAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class RoleService {
    @Autowired
    public RoleDao roleDao;

    /**
     * 添加角色
     * @param role
     * @return
     */
    public boolean addRole(Role role) {
        return roleDao.addRole(role) > 0 ? true : false;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    public boolean updateRole(Role role) {
        return roleDao.updateRole(role) > 0 ? true : false;
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    public boolean deleteRoles(Integer[] roleIds){
        return roleDao.deleteRoles(roleIds)>0?true:false;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    public List<Role> getRoleList(Role role) {
        return roleDao.getRolesByCondition(role);
    }

    /**
     * 条件统计角色数量
     * @param role
     * @return
     */
    public int countRoles(Role role){
        return roleDao.countRoles(role);
    }

    /**
     * 给角色授权
     * @param
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean grantRoleAuth(int[] auths, Integer roleId) throws Exception {
        boolean s2 = false;
        HashMap<String, Object> roleAuth = new HashMap<String, Object>();
        roleAuth.put("roleId", roleId);
        roleAuth.put("auths", roleAuth);
        try{
            roleDao.deleteAuths(roleId);
            List<RoleAuth> roleAuths=new ArrayList<RoleAuth>();
            for(int i=0;i<auths.length;i++){
                RoleAuth roleAut=new RoleAuth();
                roleAut.setRoleId(roleId);
                roleAut.setAuthId(auths[i]);
                roleAut.setStatus(1);
                roleAuths.add(roleAut);
            }
            s2 = roleDao.grantAuths(roleAuths) > 0 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!s2){
            throw  new Exception("授权失败");
        }
        return s2;
    }

    /**
     * 根据角色获取权限
     * @param roleId
     * @return
     */
    public List<Authority> getRoleAuthoritys(Integer roleId) {
        return roleDao.getRoleAuthoritys(roleId);
    }

    public Role getRole(Integer roleId){
        return roleDao.getRole(roleId);
    }
}
