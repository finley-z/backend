package com.finley.module.authority.controller;

import com.finley.core.pagination.PageVo;
import com.finley.core.respone.ResultVo;
import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.entity.Role;
import com.finley.module.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "toRole")
	public String toRole(){
		return "authority/role";
	}

	/**
	 * 添加修改角色
	 * @param role
	 * @return
	 */
	@RequestMapping("/saveRole")
	@ResponseBody
	public ResultVo insert(Role role){
		ResultVo resultVo=new ResultVo(true);
		try {
			if(role.getRoleId()==null){
				roleService.addRole(role);
			}else{
				roleService.updateRole(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVo.setStatus(false);
		}
		return resultVo;
	}

	/**
	 * 删除角色
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/deleteRoles")
	@ResponseBody
	public ResultVo deleteRoles(Integer[] roleIds){
		ResultVo resultVo=new ResultVo();
		try {
			resultVo.setStatus(roleService.deleteRoles(roleIds));
		} catch (Exception e) {
			e.printStackTrace();
			resultVo.setStatus(false);
		}
		return resultVo;
	}

	/**
	 *  角色列表
	 * @param role
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageVo<Role> getRoleList(Role role){
		PageVo<Role> pageVo=new PageVo<Role>();
		pageVo.setData(roleService.getRoleList(role));
		int count=roleService.countRoles(role);
		pageVo.setRecordsTotal(count);
		pageVo.setRecordsFiltered(count);
		return pageVo;
	}

	/**
	 *给角色授权
	 */
	@RequestMapping("/grantAuths")
	@ResponseBody
	public ResultVo grantAuth(@RequestParam int[] authIds, @RequestParam Integer roleId){
		ResultVo resultVo=new ResultVo();
		try{
			roleService.grantRoleAuth(authIds,roleId);
			resultVo.setStatus(true);
		}catch (Exception e){
			e.printStackTrace();
			resultVo.setStatus(false);
			resultVo.setErrorMsg(e.getMessage());
		}
		return resultVo;
	}

	/**
	 *获取权限
	 */
	@RequestMapping("/listAuth")
	@ResponseBody
	public List<Authority> listAuth(@RequestParam("roleId") Integer roleId){
		return roleService.getRoleAuthoritys(roleId);
	}

	/**
	 *获取权限
	 */
	@RequestMapping("/getRole")
	@ResponseBody
	public Role getRole(@RequestParam("roleId") Integer roleId){
		return roleService.getRole(roleId);
	}
}
