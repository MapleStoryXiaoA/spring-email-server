package com.wangyu.mail.service.impl;

import com.wangyu.common.exception.ServiceAccessException;
import com.wangyu.mail.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author: WangYu
 * @date: 2020-04-29
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]+([-_\\\\.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_\\\\.][a-zA-Z0-9]+)*\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration configuration;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendVerificationCode(String to) {

        //校验要发送到的邮箱
        boolean b = checkEmail(to);
        if (!b) {
            throw new ServiceAccessException(-1, "邮箱不合法");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setText(generateSixNumber());
        mailSender.send(mailMessage);

    }

    @Override
    public void sendHtmlVerificationCode(String to) {

        //校验要发送到的邮箱
        boolean b = checkEmail(to);
        if (!b) {
            throw new ServiceAccessException(-1, "邮箱不合法");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject("wy的验证码");

            Map<String, Object> params = new HashMap<>();
            params.put("verificationCode", generateSixNumber());

            Template template = configuration.getTemplate("verificationCode.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

            messageHelper.setText(html, true);//是否解析html,如果写false则以html源码格式显示
            mailSender.send(mimeMessage);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("{ }", e);
            throw new ServiceAccessException(-1, "发送邮件出错");
        }

    }

    private boolean checkEmail(String targetEmail) {
        return EMAIL_PATTERN.matcher(targetEmail).matches();
    }

    private String generateSixNumber() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
