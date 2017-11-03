package com.finley.module.user.controller;

import com.finley.common.SystemConstant;
import com.finley.common.usersession.UserSession;
import com.finley.module.user.entity.User;
import com.finley.module.user.service.UserService;
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

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author zyf
 * @date 2017/2/16
 */
@Controller
@RequestMapping(value = "/")
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "register.do")
    public String register(User user, HttpSession session, Model model) {
        user.setRegisterDate(new Date());
        user.setUserType(2);
        user.setPassword(EncryptUtil.encrypt(user.getPassword()));

        boolean isSuccess = userService.register(user);
        boolean passValidate = true;
        if (isSuccess) {
            try {
                //注册成功后，同时调用SpringSecurity验证器，进行登录
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
                Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                session.setAttribute("USER_NAME", user.getUserName());
                session.setAttribute("USER_TYPE", UserSession.getUserType());
                session.removeAttribute(SystemConstant.VALIDATE_CODE);
            } catch (UsernameNotFoundException e) {
                passValidate = false;
                e.printStackTrace();
            } catch (LockedException e) {
                passValidate = false;
                e.printStackTrace();
            } catch (BadCredentialsException e) {
                passValidate = false;
                e.printStackTrace();
            } catch (AuthenticationException e) {
                passValidate = false;
                e.printStackTrace();
            } catch (Exception e) {
                passValidate = false;
                e.printStackTrace();
            }
            if (passValidate) {
                return "redirect:/manage";
            } else {
                return "login";
            }
        } else {
            model.addAttribute("tips", "注册失败！");
            return "/register";
        }
    }
}



