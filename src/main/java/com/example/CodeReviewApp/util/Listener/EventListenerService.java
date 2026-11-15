package com.example.CodeReviewApp.util.Listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Service.EventCompletionService;
import com.example.CodeReviewApp.security.RabbitMqBeansConfig;
import com.example.CodeReviewApp.util.Listener.In.ReviewCompletedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventListenerService {

    private final EventCompletionService eventCompletionService;

    @RabbitListener(queues = RabbitMqBeansConfig.REVIEW_COMPLETED_QUEUE)
    public void handleReviewcompled(ReviewCompletedEvent event){
        System.out.println("executing listener");
        eventCompletionService.handleSuccess(event);

    }




}
