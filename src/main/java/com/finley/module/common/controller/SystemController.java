package com.finley.module.common.controller;

import com.finley.common.SystemConstant;
import com.finley.common.ValidateCode;
import com.finley.common.usersession.UserSession;
import com.finley.module.common.service.MailService;
import com.finley.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author zyf
 * @date 2017/3/8
 */
@Controller
public class SystemController {
    @Autowired
    MailService mailService;

    /***
     后台管理页面
     */
    @RequestMapping(value = "/manage")
    public String manage() {
        User user= UserSession.getUser();
        if(user!=null){
            return "manage";
        }else{
            return "redirect:/login.html";
        }

    }

    @RequestMapping(value = "/register.html")
    public String register() {
        return "/register";
    }

    @RequestMapping(value = "/login.html")
    public String login() {
        return "/login";
    }


    /**
     * 验证码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/validateCode.do",method= RequestMethod.GET)
    @ResponseBody
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/png");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session=request.getSession();

        ValidateCode validateCode = new ValidateCode(120, 32, 4, 30);
        session.setAttribute(SystemConstant.VALIDATE_CODE, validateCode.getCode());
        validateCode.write(response.getOutputStream());
    }


    public void  setContext(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletContext application = request.getServletContext();
        if(application.getAttribute("ctx")==null){
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort() + path;
            application.setAttribute("ctx",basePath);
        }

    }
}
