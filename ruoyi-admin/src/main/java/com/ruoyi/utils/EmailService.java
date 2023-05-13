package com.ruoyi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.monitor.domain.TbAlarmMedia;
import com.ruoyi.monitor.service.ITbAlarmMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.*;

@Service
public class EmailService {
    @Autowired
    private ITbAlarmMediaService alarmMediaService;

    private final static String smtpConfKey = "smtpConfKey";
    private final static String mediaType = "EMAIL";
    private final static String timeout = "20000";
    @Autowired
    private RedisCache redisCache;

    static {

    }
    private  TbAlarmMedia smtpConf(){
        String smtpConf = redisCache.getCacheObject(smtpConfKey);
        System.out.println(smtpConf+"--------");
        TbAlarmMedia alarmMedia =  strToAlarmMedia(smtpConf);
        if(StringUtils.isNull(alarmMedia)){
            alarmMedia = alarmMediaService.getAlarmMedia(mediaType);
            redisCache.setCacheObject(smtpConfKey,alarmMedia.toString(),86400);
        }
        System.out.println(alarmMedia);
        return alarmMedia;
    }
    public static TbAlarmMedia strToAlarmMedia(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, TbAlarmMedia.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean SendEmail(String receives, String subject, String content){
        //发送人邮箱号码

        TbAlarmMedia smtpConf = smtpConf();
        try {
            //1、创建参数配置, 用于连接邮件服务器的参数配置
            Properties prop = new Properties();
            //发送邮件协议名称
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.host", smtpConf.getHost());//邮件服务器地址
            prop.setProperty("mail.smtp.port", String.valueOf(smtpConf.getPort()));
            prop.setProperty("mail.smtp.auth", "true");//需要请求认证
            //prop.put("mail.smtp.port","587");
            prop.setProperty("mail.debug", "true");
            prop.put("mail.smtp.connectiontimeout", timeout);
            prop.put("mail.smtp.timeout", timeout);
            if(StringUtils.isNotNull(smtpConf.getEnableSsl())){

            }
            if(StringUtils.isNotNull(smtpConf.getEnableTls())){
                prop.put("mail.smtp.starttls.enable", "true");
            }

            // 2. 根据参数配置，创建会话对象, 用于和邮件服务器交互
            Session session= Session.getInstance(prop);


            MimeMessage message = createMimeMessage(session,subject,content,smtpConf.getUsername(), receives);
            Transport transport = session.getTransport();
            // 5. 使用 邮箱账号和密码（授权码）连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(smtpConf.getUsername(), smtpConf.getPasswd());
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public MimeMessage createMimeMessage(Session session,String subject,String content, String sendMail, String receives) throws Exception {

        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "发件人名称", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）、收件人昵称

        List<String>  recipients = Arrays.asList(receives.split(","));
        InternetAddress[] addresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);
        message.setSubject(subject,"UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    public static void main(String[] args) {
//        String smtpConf = redisCache.getCacheObject(smtpConfKey);
//        System.out.println(smtpConf);
//        TbAlarmMedia alarmMedia =  strToAlarmMedia(smtpConf);
//
//        System.out.println(alarmMedia);
    }

}

