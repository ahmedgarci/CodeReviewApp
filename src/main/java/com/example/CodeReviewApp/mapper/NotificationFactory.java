package com.example.CodeReviewApp.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.Notification;

@Component
public class NotificationFactory {
    
    public Notification create(String title,String message,Long userId){
        return Notification.builder().title(title).message(message).sent_at(LocalDateTime.now()).userId(userId).build();
    }

}
