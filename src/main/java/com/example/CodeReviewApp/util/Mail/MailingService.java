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
    
    String invitationUrl = "http://localhost:5173/invitation?code=" + token;
    String body = String.format(
            """
            You have received an invitation to collaborate on a project.

            Click the link below to accept the invitation:
            %s
            """,
            invitationUrl
            );
          
    message.setTo(to);
    
    message.setSubject(subject);
    
    message.setText(body);

    mailSender.send(message);
    
    }

}
