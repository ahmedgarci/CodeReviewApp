package com.example.CodeReviewApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull; 

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final WebsocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {

        registry.addEndpoint("/ws/connect").setAllowedOriginPatterns("http://localhost:5173");

    }
    
    @Override
    public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {

        registration.interceptors(webSocketAuthInterceptor);
    }

    @Override
    public void configureMessageBroker(@NonNull  MessageBrokerRegistry registry) {
    
        registry.enableSimpleBroker("/broker");

        registry.setUserDestinationPrefix("/user");
    }


    
}