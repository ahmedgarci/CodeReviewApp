package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.dto.Authentication.In.LoginDto;
import com.example.CodeReviewApp.dto.Authentication.In.RegistrationDto;
import com.example.CodeReviewApp.dto.Authentication.Out.SuccessFulAuthentication;
import com.example.CodeReviewApp.security.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegistrationDto request) {
        
        authenticationService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<SuccessFulAuthentication> authenticateUser(@RequestBody LoginDto request) {
        
        return ResponseEntity.ok(authenticationService.authenticateUser(request));    
    }



    

}
