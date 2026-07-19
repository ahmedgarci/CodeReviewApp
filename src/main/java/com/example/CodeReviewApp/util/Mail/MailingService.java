package com.example.CodeReviewApp.util.Mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailingService {
    
    private final JavaMailSender mailSender;
    
    @Async
    public void sendEmail(String to, String subject, String token) {

    SimpleMailMessage message = new SimpleMailMessage();

    String body = String.format("u have received an invtation collaborate in a project . code : %s", token);

    message.setTo(to);
    
    message.setSubject(subject);
    
    message.setText(body);

    mailSender.send(message);
    
    }

}
