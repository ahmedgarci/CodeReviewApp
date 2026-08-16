package com.example.CodeReviewApp.Service.Implementations;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.NotificationRepository;
import com.example.CodeReviewApp.Service.NotificationService;
import com.example.CodeReviewApp.dto.Notification.Out.Notification;
import com.example.CodeReviewApp.mapper.NotificationFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl  implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationFactory notificationFactory;
    private final AuthenticationContext authenticationContext;

    @Override
    public void sendNotification(Notification notification,String  toUser) {

        messagingTemplate.convertAndSendToUser(toUser.toString(),"/notifications",notification);

    }

    @Override
    public List<Notification> getAllNotifications() {
        User user = authenticationContext.getCurrentUser();

        return notificationRepository.getUserNotifications(user.getId()).stream().map(n -> notificationFactory.toNotificationResponse(n)).toList();

    }
    
}
