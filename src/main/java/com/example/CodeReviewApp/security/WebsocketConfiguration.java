package com.example.CodeReviewApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import org.springframework.lang.NonNull; 

@Configuration
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {

        registry.addEndpoint("/ws/connect");

    }

    @Override
    public void configureMessageBroker(@NonNull  MessageBrokerRegistry registry) {
    
        registry.enableSimpleBroker("/broker");

        registry.setUserDestinationPrefix("/user");
    }
    
}