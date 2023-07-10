package com.example.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUseCase {

    @Autowired
    JavaMailSender javaMailSender;

    public SendEmailUseCase(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void execute(String to, String subject, String content) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);

        javaMailSender.send(email);
    }
}
