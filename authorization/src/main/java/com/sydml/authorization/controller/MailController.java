package com.sydml.authorization.controller;

import com.sydml.authorization.service.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuym
 * @date 2019/5/28 0028
 */
@RestController
@RequestMapping("mail")
public class MailController {
    @Autowired
    private MailServiceImpl mailService;

    @GetMapping("send")
    public void sendMail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("content") String content) {
        for (int i = 0; i < 10; i++) {
            mailService.sendSimpleMail(to, subject+i, content+i);
        }
    }

    @GetMapping("send-attach")
    public void sendAttachmentsMail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("content") String content) {
        mailService.sendAttachmentsMail(to, subject, content,null);
    }
}
