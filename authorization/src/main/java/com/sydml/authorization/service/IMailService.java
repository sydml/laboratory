package com.sydml.authorization.service;

import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Liuym
 * @date 2019/5/28 0028
 */
public interface IMailService {
    /**
     * 简单邮件发送，不带附件
     * @param to
     * @param subject
     * @param content
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件，可以增加多个附件
     * @param to
     * @param subject
     * @param content
     */
    void sendAttachmentsMail(String to, String subject, String content, MultipartFile inputSource);
}
