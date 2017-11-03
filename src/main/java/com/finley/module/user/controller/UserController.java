package com.finley.module.user.controller;

import com.finley.common.SystemConstant;
import com.finley.common.usersession.UserSession;
import com.finley.module.authority.entity.Authority;
import com.finley.module.authority.service.AuthorityService;
import com.finley.module.authority.service.RoleService;
import com.finley.module.user.entity.User;
import com.finley.module.user.service.UserService;
import com.finley.util.CookieUtil;
import com.finley.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyf
 * @date 2017/3/13
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthenticationManager authenticationManager;


    /**
     * 用户登录
     *
     * @param user
     * @param model
     * @param session
     * @param validateCode
     * @param remember
     * @return
     */
    @RequestMapping(value = "login.do")
    public String login(User user, Model model, HttpSession session, @RequestParam String validateCode, @RequestParam(required = false) Integer remember) {
        boolean passValidate = true;
        String validateCodeS = (String) session.getAttribute(SystemConstant.VALIDATE_CODE);
        //对用户输入密码进行加密，以便与数据库密码比较
        user.setPassword(EncryptUtil.encrypt(user.getPassword()));
        if (user.getUserName() == null || "".equals(user.getUserName())) {
            model.addAttribute("userNameTips", "请输入用户名！");
            passValidate = false;
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            model.addAttribute("passwordTips", "请输入密码！");
            passValidate = false;
        }
        if (validateCode == null || "".equals(validateCode)) {
            model.addAttribute("validateTips", "请输入验证码！");
            passValidate = false;
        }
        if (validateCodeS == null || "".equals(validateCodeS)) {
            model.addAttribute("validateTips", "验证码不正确！");
            passValidate = false;
        }
        if (validateCodeS != null && !validateCodeS.toLowerCase().equals(validateCode.toLowerCase())) {
            model.addAttribute("validateTips", "验证码不正确！");
            passValidate = false;
        } else {
            try {
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
                //调用loadUserByUsername
                Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 这个非常重要，否则验证后将无法登陆
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                session.setAttribute("USER_NAME", user.getUserName());
                session.setAttribute("USER_TYPE", UserSession.getUserType());
                if (remember != null && remember == 1) {
                    CookieUtil.addCookie("REMENBER_NAME", user.getUserName(), 60 * 60 * 24 * 7);
                }
                session.removeAttribute(SystemConstant.VALIDATE_CODE);
            } catch (UsernameNotFoundException e) {
                model.addAttribute("userNameTips", "用户不存在！");
                passValidate = false;
                e.printStackTrace();
            } catch (LockedException e) {
                model.addAttribute("userNameTips", "用户被锁定，请联系管理员！");
                passValidate = false;
                e.printStackTrace();
            } catch (BadCredentialsException e) {
                model.addAttribute("passwordTips", "密码错误！");
                passValidate = false;
                e.printStackTrace();
            } catch (AuthenticationException e) {
                model.addAttribute("userNameTips", e.getMessage());
                passValidate = false;
                e.printStackTrace();
            } catch (Exception e) {
                model.addAttribute("userNameTips", "系统繁忙！");
                passValidate = false;
                e.printStackTrace();
            }
        }
        if (passValidate) {
            return "redirect:/manage";
        } else {
            model.addAttribute("input", user.getUserName());
            return "/login";
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "getUserInfo.do")
    @ResponseBody
    public User getUserInfo() {
        return UserSession.getUser();
    }

    /**
     * 获取用户权限菜单
     *
     * @return
     */
    @RequestMapping(value = "getAuthMenu")
    @ResponseBody
    public List<Authority> getAuthMenu() {
        Integer roleId = UserSession.getRoleId();
        if (roleId == null) {
            return null;
        }
        return roleService.getRoleAuthoritys(roleId);
    }

    /**
     * 用户注销登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do")
    public String logout(HttpSession session) {
        try {
            session.invalidate();
            return "redirect:/manage";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/manage";
        }
    }

    @ResponseBody
    @RequestMapping(value = "modifyPassword")
    public Map<String, Object> modifyPassword(User user, @RequestParam(required = false) String orignalPassword) {
        Map<String, Object> res = new HashMap<String, Object>();
        Integer userId = UserSession.getUserId();
        user.setUserId(userId);
        //对用户输入密码进行加密，以便与数据库密码比较
        user.setPassword(EncryptUtil.encrypt(user.getPassword()));
        orignalPassword = EncryptUtil.encrypt(orignalPassword);
        user.setComfirmPsw(EncryptUtil.encrypt(user.getComfirmPsw()));
        User store = userService.getUser(userId);

        boolean success = true;
        try {
            if (user.getPassword() == null || "".equals(user.getPassword())) {
                res.put("passwordTips", "密码不能为空！");
                success = false;
            }
            if (!store.getPassword().equals(orignalPassword)) {
                res.put("orignalPasswordTips", "原始密码错误！");
                success = false;
            }
            if (!user.getPassword().equals(user.getComfirmPsw())) {
                res.put("confirmPasswordTips", "两次密码不一致！");
                success = false;
            }
            if (!success) {
                res.put("status", false);
                res.put("tips", "密码修改失败!");
            } else {
                userService.modifyPassword(user);
                res.put("status", true);
                res.put("tips", "密码修改成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", false);
            res.put("tips", "密码修改失败!");
        }

        return res;
    }


}

