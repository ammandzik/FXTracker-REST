package com.FXTracker.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void send(String receiver, String content) {
        System.out.println(String.format("Sending email to %s : %s", receiver, content));
    }
}
