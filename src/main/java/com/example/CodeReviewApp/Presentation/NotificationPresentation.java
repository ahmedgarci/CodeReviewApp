package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.Service.NotificationService;
import com.example.CodeReviewApp.dto.Notification.Out.Notification;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationPresentation {

    private final NotificationService notificationService;


    @GetMapping("/")
    public ResponseEntity<List<Notification>> getUserNotification() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
    

}
