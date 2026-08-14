package com.example.CodeReviewApp.Presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.CodeReviewApp.dto.Authentication.In.LoginDto;
import com.example.CodeReviewApp.dto.Authentication.Out.SuccessFulAuthentication;
import com.example.CodeReviewApp.security.AuthenticationService;
import com.example.CodeReviewApp.security.JwtService;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationPresentationTest {

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;    

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void shouldRegister()throws Exception{

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
            {
                "email":"aaaaaaa@gmail.com",
                "username":"ahmed",
                "password":"AAAAAA122"
            }
        """)).andExpect(status().isCreated());

            verify(authenticationService).register(any());
    }

    @Test
    public void shouldLogin()throws Exception{

        SuccessFulAuthentication response  = new SuccessFulAuthentication("aa","email","faleToke",LocalDateTime.now());

        when(authenticationService.authenticateUser(any(LoginDto.class))).thenReturn(response);

        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
            {
                "email":"aaaaaaa@gmail.com",
                "password":"AAAAAA122"
            }
        """)).andExpect(status().isOk());

        verify(authenticationService).authenticateUser(any(LoginDto.class));

    }

    @Test
    void shouldReturn400WhenRegisterEmailIsInvalid() throws Exception {

    mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "email": "invalid-email",
                            "username": "ahmed",
                            "password": "AAAAAA122"
                        }
                    """)
    )
    .andExpect(status().isBadRequest());

    verify(authenticationService, never()).register(any());

    }

    @Test
    void shouldReturn400WhenRegisterFieldsAreEmpty() throws Exception {

    mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "email": "",
                            "username": "",
                            "password": ""
                        }
                    """)
    ).andExpect(status().isBadRequest());

    verify(authenticationService, never()).register(any());

    }

    @Test
    void shouldReturn400WhenLoginWithEmailInvalid() throws Exception {

    mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "email": "invalid-email",
                            "password": ""
                        }
                    """)
    ).andExpect(status().isBadRequest());

    verify(authenticationService, never()).authenticateUser(any());

    }



}




