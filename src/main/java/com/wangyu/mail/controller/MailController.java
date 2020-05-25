package com.wangyu.mail.controller;


import com.wangyu.common.ApiResponse;
import com.wangyu.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

/**
 * @author: WangYu
 * @date: 2020-04-29
 */

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/verification_code")
    public ApiResponse sendVerificationCode(@RequestParam(value = "to", required = true) String to) {
        //简单的文本
        //mailService.sendVerificationCode(to);

        //基于freeMaker模板的html
        mailService.sendHtmlVerificationCode(to);

        return ApiResponse.OK();
    }
}
