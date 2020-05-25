package com.wangyu.mail.service;

/**
 * @author: WangYu
 * @date: 2020-04-29
 */
public interface MailService {

    void sendVerificationCode(String to);

    void sendHtmlVerificationCode(String to);
}
