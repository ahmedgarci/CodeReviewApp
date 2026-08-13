package com.example.CodeReviewApp.Service;

import com.example.CodeReviewApp.dto.Notification.Out.Notification;

public interface NotificationService {
    
    public void sendNotification(Notification notification,Long to_user);
    public void getAllNotifications(Long userId);

}
