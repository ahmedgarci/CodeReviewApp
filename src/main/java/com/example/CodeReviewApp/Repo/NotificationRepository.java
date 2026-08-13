package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.Models.Notification;

public interface NotificationRepository {

    void insert(Notification notification);

    List<Notification> getUserNotifications(Long userId);
}
