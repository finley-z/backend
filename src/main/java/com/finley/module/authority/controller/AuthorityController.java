package com.finley.module.authority.controller;

import com.finley.core.respone.ResultVo;
import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 郑远锋 on 2017/3/5.
 */

@Controller
@RequestMapping(value = "/authority")
public class AuthorityController {
    @Autowired
    AuthorityService authorityService;

    @RequestMapping(value = "authorityList")
    public String toAuthority(){
        return "authority/authority";
    }

    /**
     * 添加修改权限
     * @param authority
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveAuth")
    public ResultVo addAuthority(Authority authority){
        ResultVo resultVo=new ResultVo(true);
        try{
            if(authority.getId()==null){
               authorityService.addAuthority(authority);
            }else{
                authorityService.updateAuthority(authority);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultVo.setStatus(false);
        }
        return resultVo;
    }

    /**
     * 删除权限
     * @param authIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteAuths")
    public ResultVo deleteAuths(Integer[] authIds){
        ResultVo resultVo=new ResultVo(true);
        try{
             authorityService.delteAuths(authIds);
        }catch (Exception e){
            e.printStackTrace();
            resultVo.setStatus(false);
        }
        return resultVo;
    }

       /***
     * 查找父级权限
     * @param authority
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findParentAuthority")
    public List<Authority> findParentAuthority(Authority authority){
        authority.setLevel(1);
        return authorityService.findByCondition(authority);
    }

    /***
     * 查找权限关系图
     * @param authority
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findAuthorityMap")
    public List<Authority> getAuthorityMap(Authority authority){
        return authorityService.getAuthorityMap(authority);
    }


    /***
     * 查找权限对象
     * @param authId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findAuthority")
    public Authority findAuthority(Integer authId){
        return authorityService.getAuthority(authId);
    }
}
