package com.example.CodeReviewApp.util.Producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.security.RabbitMqBeansConfig;
import com.example.CodeReviewApp.util.Producer.out.Event;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer{

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void execute(Event event) {

        rabbitTemplate.convertAndSend(RabbitMqBeansConfig.REVIEW_EXCHANGE,RabbitMqBeansConfig.REVIEW_ROUTING_KEY,event);

    }
    
    
}
