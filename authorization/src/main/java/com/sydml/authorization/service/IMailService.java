package com.sydml.authorization.service;

/**
 * @author Liuym
 * @date 2019/5/28 0028
 */
public interface IMailService {
    void sendSimpleMail(String to, String subject, String content);
}
