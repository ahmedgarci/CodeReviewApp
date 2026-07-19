package com.example.CodeReviewApp.util.Auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.User;

@Component
public class AuthenticationContextImpl implements AuthenticationContext {

    @Override
    public User getCurrentUser() {
    
      Authentication authentication =   SecurityContextHolder.getContext().getAuthentication();

      User connectedUser = (User)authentication.getPrincipal();

      return connectedUser;
    }
    
}
