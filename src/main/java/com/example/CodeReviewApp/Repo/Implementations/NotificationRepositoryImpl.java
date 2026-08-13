package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.NOTIFICATIONS;

import java.util.List;
import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.Notification;
import com.example.CodeReviewApp.Repo.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl  implements NotificationRepository{

    private final DSLContext dsl;

    @Override
    public void insert(Notification notification) {

        dsl.insertInto(NOTIFICATIONS).columns(NOTIFICATIONS.TITLE,NOTIFICATIONS.MESSAGE,NOTIFICATIONS.USER_ID,NOTIFICATIONS.TYPE)
        .values(notification.getTitle(),notification.getMessage(),notification.getUserId(),notification.getType()).execute();
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        
        return dsl.selectFrom(NOTIFICATIONS).where(NOTIFICATIONS.USER_ID.eq(userId)).fetchInto(Notification.class);

    }


    
}
