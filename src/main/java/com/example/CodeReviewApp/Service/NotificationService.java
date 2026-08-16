package com.example.CodeReviewApp.Service;

import java.util.List;

import com.example.CodeReviewApp.dto.Notification.Out.Notification;

public interface NotificationService {
    
    public void sendNotification(Notification notification,String to_user);
    public List<Notification> getAllNotifications();

}
