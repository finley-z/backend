package com.finley.module.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author zyf
 * @date 2017/5/23
 */
@Service
public class MailService {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage mailMessage;

    public void sendMail(String receiverMail, String subject, String content) {
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setTo(receiverMail);
        mailSender.send(mailMessage);
    }
}
