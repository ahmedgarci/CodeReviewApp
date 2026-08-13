package com.example.CodeReviewApp.Service.Implementations;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Repo.NotificationRepository;
import com.example.CodeReviewApp.Service.NotificationService;
import com.example.CodeReviewApp.dto.Notification.Out.Notification;
import com.example.CodeReviewApp.mapper.NotificationFactory;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl  implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationFactory notificationFactory;

    @Override
    public void sendNotification(Notification notification,Long to_user_Id) {



    }

    @Override
    public void getAllNotifications(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllNotifications'");
    }
    
}
