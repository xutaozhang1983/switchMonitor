package com.ruoyi.monitor.media;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.monitor.domain.TbAlarmMedia;
import com.ruoyi.monitor.service.ITbAlarmMediaService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class EmailService {

    private final static String smtpConfKey = "smtpConfKey";
    private final static String mediaType = "EMAIL";
    private final static String timeout = "20000";

    private final static ITbAlarmMediaService alarmMediaService;

    static {
        alarmMediaService = SpringUtils.getBean(ITbAlarmMediaService.class);
    }



    private static TbAlarmMedia smtpConf(){
        TbAlarmMedia alarmMedia = alarmMediaService.selectAlarmMedia(mediaType);
        if(!alarmMedia.getStatus().equals("0")){
            return null;
        }
        return alarmMedia;
    }

    /**
     *
     * @param receives 多个收件人逗号隔开
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return
     */
    public static AjaxResult SendEmail(String receives, String subject, String content){
        //发送人邮箱号码

        TbAlarmMedia smtpConf = smtpConf();
        if (StringUtils.isNull(smtpConf)){
            return AjaxResult.error("没有找到可用smtp配置!");
        }
        try {
            // 2. 根据参数配置，创建会话对象, 用于和邮件服务器交互
            Session session= Session.getInstance(createProp(smtpConf));
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
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    private static Properties createProp(TbAlarmMedia smtpConf) throws GeneralSecurityException {
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
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.socketFactory", sf);//设置ssl端口
        }
        if(StringUtils.isNotNull(smtpConf.getEnableTls())){
            prop.put("mail.smtp.starttls.enable", "true");
        }
        return prop;
    }
    private static MimeMessage createMimeMessage(Session session,String subject,String content, String sendMail, String receives) throws Exception {

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


}

