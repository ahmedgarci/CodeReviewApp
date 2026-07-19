package com.example.CodeReviewApp.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.dto.Authentication.In.RegistrationDto;

@Component
public class UserFactory {
    
    public User toUser(RegistrationDto registrationDto){

        return User.builder()
                .email(registrationDto.email())
                .username(registrationDto.username())
                .password(registrationDto.password())
                .created_at(LocalDate.now())
                .build();
    }
}
