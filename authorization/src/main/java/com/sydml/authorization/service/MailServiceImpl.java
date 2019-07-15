package com.sydml.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author Liuym
 * @date 2019/5/28 0028
 */
@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (Exception e) {
        }
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, MultipartFile inputSource) {
        String [] fileArray={"C:\\Users\\Administrator\\Desktop\\项目修改截图\\feature1\\未占用费用清单修改.PNG","C:\\Users\\Administrator\\Desktop\\mjxy_cloud2.0.zip"};
        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            //验证文件数据是否为空
            if(null != fileArray){
                FileSystemResource file=null;
                for (int i = 0; i < fileArray.length; i++) {
                    //添加附件
                    file=new FileSystemResource(fileArray[i]);
//                    helper.addAttachment(fileArray[i].substring(fileArray[i].lastIndexOf(File.separator)), file);
                    helper.addAttachment(inputSource.getOriginalFilename(), inputSource);
                }
            }
            mailSender.send(message);
            System.out.println("带附件的邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("发送带附件的邮件失败");
        }
    }
}
