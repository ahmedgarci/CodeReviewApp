package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.UserRepository;
import com.example.CodeReviewApp.dto.Authentication.In.LoginDto;
import com.example.CodeReviewApp.dto.Authentication.In.RegistrationDto;
import com.example.CodeReviewApp.exceptions.BadCredentials;
import com.example.CodeReviewApp.exceptions.RessourceAlreadyExists;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.mapper.UserFactory;
import com.example.CodeReviewApp.security.AuthenticationService;
import com.example.CodeReviewApp.security.JwtService;
import com.example.CodeReviewApp.util.Auth.AuthenticationGuard;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserFactory userFactory;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationGuard authenticationGuard;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void shouldRegisterUser(){

        String email = "fake@gmail.com";

        RegistrationDto registrationDto =  new RegistrationDto(email,"","");

        User user = new User();

        when(userRepository.checkExistsByEmail(email)).thenReturn(false);

        when(userFactory.toUser(registrationDto)).thenReturn(user);

        authenticationService.register(registrationDto);

        verify(userFactory).toUser(any(RegistrationDto.class));

        verify(userRepository).checkExistsByEmail(email);

        verify(userRepository).insertUser(user);

    }

    @Test
    public void shouldThrowWhenEmailExists(){
        String email = "fake@gmail.com";

        RegistrationDto registrationDto =  new RegistrationDto(email,"","");

        when(userRepository.checkExistsByEmail(email)).thenReturn(true);

        assertThrows(RessourceAlreadyExists.class,()->  authenticationService.register(registrationDto));

        verify(userRepository).checkExistsByEmail(email);

        verifyNoInteractions(userFactory);

        verify(userRepository,never()).insertUser(any(User.class));

    }

    @Test
    public void shouldAuthenticateSuccessfully(){

        LoginDto loginDto = new LoginDto("fake@gmail.com", "aaaaaaaaaaa");

        User user = new User();

        user.setEmail("fake@gmail.com");

        user.setPassword("encodedPassword");

        String token = "Bearer eyxxx";

        when(userRepository.findByEmail(loginDto.email())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginDto.password(), user.getPassword())).thenReturn(true);

        when(jwtService.generateJwt(any(), any(), any())).thenReturn(token);

        authenticationService.authenticateUser(loginDto);

        verify(userRepository).findByEmail(loginDto.email());

        verify(passwordEncoder).matches(loginDto.password(),user.getPassword());

        verify(jwtService).generateJwt(any(), any(), any());

        verify(authenticationGuard).connectUser(any(),any(),any());
    
    }

    @Test
    public void shouldThrowWhenEmailNotFound(){

        LoginDto loginDto =new LoginDto("fake@gmail.com","aaaaaaaaaaa");

        when(userRepository.findByEmail(loginDto.email())).thenReturn(Optional.empty());

        assertThrows(RessourceNotFoundException.class, ()->  authenticationService.authenticateUser(loginDto));

        verify(userRepository).findByEmail(loginDto.email());

        verifyNoInteractions(passwordEncoder);

        verifyNoInteractions(jwtService);

        verifyNoInteractions(authenticationGuard);
    }

    @Test
    public void shouldThrowWhenPasswordNotValid(){

        LoginDto loginDto = new LoginDto("fake@gmail.com", "aaaaaaaaaaa");

        User user = new User();

        user.setEmail("fake@gmail.com");

        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginDto.email())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginDto.password(), user.getPassword())).thenReturn(false);

        assertThrows(BadCredentials.class, ()-> authenticationService.authenticateUser(loginDto));

        verify(userRepository).findByEmail(loginDto.email());

        verify(passwordEncoder).matches(loginDto.password(),user.getPassword());

        verifyNoInteractions(jwtService);

        verifyNoInteractions(authenticationGuard);


    }

    
}

