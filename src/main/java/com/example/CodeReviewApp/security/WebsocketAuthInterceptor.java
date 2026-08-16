package com.example.CodeReviewApp.security;

import java.util.Collections;

import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebsocketAuthInterceptor  implements ChannelInterceptor{

    private final JwtService jwtService;

    public Message<?> preSend(@NonNull Message<?> message,@NonNull  MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

        if(accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())){

            String authorization = accessor.getFirstNativeHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer "))     throw new BadCredentialsException("Missing JWT");

            String token = authorization.substring(7);

            String username = jwtService.getSubject(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,Collections.emptyList());

            accessor.setUser(authentication);

        }

	    return message;

    }
}
