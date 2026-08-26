package com.example.CodeReviewApp.security.Auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.security.JwtService;
import com.example.CodeReviewApp.util.Auth.AuthenticationGuard;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler{

    private final AuthenticationGuard authenticationGuard;
    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        System.out.println("disconnecting :");

        var header = request.getHeader("Authorization");
        
        if(header == null || !header.startsWith("Bearer ")){
            return;
        }
    
        String token = header.substring(7);

        try {
            String email = jwtService.getSubject(token);

            authenticationGuard.disconnectUser(email);

        } catch (JwtException e) {
            e.printStackTrace();
        }
    }

    
}
