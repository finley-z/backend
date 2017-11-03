package com.finley.module.authority.entity;

import com.finley.module.common.entity.BaseEntity;

/**
 * Created by 郑远锋 on 2017/4/5.
 */
public class RoleAuth extends BaseEntity{
    private Integer roleId;
    private Integer authId;
    private Integer status;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
