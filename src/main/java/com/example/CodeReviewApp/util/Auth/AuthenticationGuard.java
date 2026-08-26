package com.example.CodeReviewApp.util.Auth;

import java.time.LocalDateTime;


import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.security.Cache.JwtSession;
import com.github.benmanes.caffeine.cache.Cache;

import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class AuthenticationGuard {

    private final Cache<String, JwtSession> authenticatedUsers ;

    public boolean checkUserConnectivity(@NonNull String email,@NonNull String token) {

        JwtSession session = authenticatedUsers.getIfPresent(email);

        if(session == null || !session.token().equals(token) || session.expiresAt().isBefore(LocalDateTime.now()) ) {

            disconnectUser(email);            

            return false;
        }

        return true;
    }

    public void connectUser(String email,String token,LocalDateTime expiresAt){

        JwtSession session = new JwtSession(token, expiresAt);

        authenticatedUsers.put(email, session);

    }

    public void disconnectUser(String email){

        authenticatedUsers.invalidate(email);

    }

  
}