package com.FXTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public void send(String receiver, String content, String subject) {

        var message = new SimpleMailMessage();
        message.setFrom("fxtracker-service@gmail.com");
        message.setTo(receiver);
        message.setText(content);

        mailSender.send(message);
    }
}
