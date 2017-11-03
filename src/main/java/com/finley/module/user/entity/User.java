package com.finley.module.user.entity;

import com.finley.enums.UserStatusEnum;
import com.finley.module.authority.entity.Authority;
import com.finley.module.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * 基础用户类
 * @author zyf
 * @date 2017/2/16
 */

public class User extends BaseEntity {
    private Integer userId;        //用户ID
    private Integer userType;     //用户类型 1、系统管理员用户 2代理商用户

    @Size(min=3, max=30) @NotEmpty
    private String userName;
    private String password;
    private String comfirmPsw;
    private String realName;
    private Integer gender;
    private String email;
    private String phone;
    private Integer status;
    private String  statusDesc;
    private Boolean enabled;
    private Integer roleId;
    private Set<Authority> authoritySet;
    private Date registerDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public String getComfirmPsw() {
        return comfirmPsw;
    }

    public void setComfirmPsw(String comfirmPsw) {
        this.comfirmPsw = comfirmPsw;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Set<Authority> getAuthoritySet() {
        return authoritySet;
    }

    public void setAuthoritySet(Set<Authority> authoritySet) {
        this.authoritySet = authoritySet;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }


    public String getStatusDesc() {
        if(this.status!=null){
            return UserStatusEnum.getDesc(this.status);
        }else{
            return "";
        }
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
