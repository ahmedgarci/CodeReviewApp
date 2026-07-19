package com.example.CodeReviewApp.util.Auth;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.stereotype.Component;



@Component
public class AuthenticationGuard {


    private final Map<String, AuthenticationSession> authenticatedUsers = new ConcurrentHashMap<>();


    public boolean checkUserConnectivity(String email) {

        AuthenticationSession session = authenticatedUsers.get(email);

        if(session == null) {
            return false;
        }

        if(session.expiresAt().isBefore(LocalDateTime.now())) {
            authenticatedUsers.remove(email);
            return false;
        }

        return true;
    }

    public void connectUser(String email,String token,LocalDateTime expiresAt){

        AuthenticationSession session = new AuthenticationSession(token, expiresAt);

        authenticatedUsers.put(email, session);

    }

    public void disconnectUser(String email){

        authenticatedUsers.remove(email);

    }

  
}