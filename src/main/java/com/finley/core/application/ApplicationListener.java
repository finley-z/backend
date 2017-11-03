package com.finley.core.application;

import com.finley.module.common.service.MailService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author zyf
 * @date 2017/5/23
 */
public class ApplicationListener implements ServletContextListener {

    private WebApplicationContext springContext;
    private MailService mailService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        if(springContext != null){
            mailService = (MailService) springContext.getBean("mailService");
        }else{
            System.out.println("获取SpringContext失败！");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(mailService!=null){
            mailService.sendMail("zhengyuanfeng@wanhengtech.com","系统故障","千机变报价系统已关闭，请联系相关人员排除故障！");
        }
    }


}
