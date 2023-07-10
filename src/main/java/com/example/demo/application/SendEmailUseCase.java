package com.example.demo.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendEmailUseCase {

    @Autowired
    JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public SendEmailUseCase(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void execute(String to, String subject, String content, Context context) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        //SimpleMailMessage email = new SimpleMailMessage();

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = templateEngine.process(content, context);
        //System.out.println(context.getVariable("newPassword"));

        String replacedHtmlContent = htmlContent.replace("[[newPassword]]", context.getVariable("newPassword").toString());
        helper.setText(replacedHtmlContent, true);

        javaMailSender.send(message);
    }
}
