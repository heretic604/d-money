package com.heretic.dmoney.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl {

    private final JavaMailSender mailSender;

    //    @Override
    public void sendSuccessfulOrderMail(String email) {
        SimpleMailMessage mailMessage = getSimpleMailMessage(email);
        mailMessage.setText("MESSAGE_MAIL_ORDER");
        mailMessage.setSubject("SUBJECT_MAIL_ORDER");
        mailSender.send(mailMessage);
    }

    //    @Override
    public void sendSuccessfulRegistrationMail(String email) {
        SimpleMailMessage mailMessage = getSimpleMailMessage(email);
        mailMessage.setText("MESSAGE_MAIL_REGISTRATION");
        mailMessage.setSubject("SUBJECT_MAIL_REGISTRATION");
        mailSender.send(mailMessage);
    }

    private SimpleMailMessage getSimpleMailMessage(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(email);
        return mailMessage;
    }
}