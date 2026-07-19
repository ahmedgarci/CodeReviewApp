package com.example.CodeReviewApp.security;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.UserRepository;
import com.example.CodeReviewApp.dto.Authentication.In.LoginDto;
import com.example.CodeReviewApp.dto.Authentication.In.RegistrationDto;
import com.example.CodeReviewApp.dto.Authentication.Out.SuccessFulAuthentication;
import com.example.CodeReviewApp.exceptions.BadCredentials;
import com.example.CodeReviewApp.exceptions.RessourceAlreadyExists;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.mapper.UserFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationGuard;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationGuard authenticationGuard;

    
    public void register(RegistrationDto registrationDto){

        Boolean exists = userRepository.checkExistsByEmail(registrationDto.email());

        if (exists) {
            throw new RessourceAlreadyExists("email already exists");
        }

        User user = userFactory.toUser(registrationDto);

        user.setPassword(encryptPassword(user.getPassword()));

        userRepository.insertUser(user);

    }

    public SuccessFulAuthentication authenticateUser(LoginDto loginDto){

        User user = userRepository.findByEmail(loginDto.email()).orElseThrow(()-> new RessourceNotFoundException("user with email was not found"));

        if(!verifyPassword(loginDto.password(), user.getPassword())){

            throw new BadCredentials("bad credentials ");

        }

        LocalDateTime tokenExpiration = LocalDateTime.now().plusHours(1);

        String token = jwtService.generateJwt(user.getEmail(), new HashMap<>(),tokenExpiration);

        authenticationGuard.connectUser(token, token, tokenExpiration);

        return new SuccessFulAuthentication(user.getUsername(),user.getEmail(), token,tokenExpiration);

    }

    private boolean verifyPassword(String userInput,String encryptedPassword){

        return passwordEncoder.matches(userInput,encryptedPassword);

    }

    private String encryptPassword(String textPassword){

        return passwordEncoder.encode(textPassword);
    }
    
}
