package com.example.CodeReviewApp.Service;

import java.util.List;

import com.example.CodeReviewApp.dto.Notification.Out.Notification;

public interface NotificationService {
    
    public void sendNotification(String to_user,Long toUserId);
    public List<Notification> getAllNotifications();

}
