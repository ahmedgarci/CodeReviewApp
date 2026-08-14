package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.CodeReviewApp.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService = new JwtService();

    @Test
    public void shouldGenerateJwt(){

        String username = "ahmed@mail.com";

        String token = jwtService.generateJwt(username, Map.of(), LocalDateTime.now().plusHours(1));

        assertNotNull(token);

        assertFalse(token.isBlank());

    }
    @Test
    public void shouldGetSubjectFromToken(){

        String username = "ahmed@gmail.com";

        String token = jwtService.generateJwt(username,Map.of(),LocalDateTime.now().plusHours(1));

        String subject = jwtService.getSubject(token);

        assertEquals(username, subject);
    }

    @Test
    public void shouldReturnTrueWhenTokenIsValid(){

        String username = "ahmed@gmail.com";

        String token = jwtService.generateJwt(username,Map.of(),LocalDateTime.now().plusHours(1));

        boolean result = jwtService.isTokenValid(token, username);

        assertTrue(result);
    }
    @Test
    public void shouldReturnFalseWhenTokenNotValid(){

        String username = "ahmed@gmail.com";

        String token = jwtService.generateJwt("aaa@gmail.com",Map.of(),LocalDateTime.now().plusHours(1));

        boolean result = jwtService.isTokenValid(token, username);

        assertFalse(result);

    }

    @Test
    public void shouldReturnFalseWhenTokenNotValidDueToExpiration(){

        String username = "ahmed@gmail.com";

        String token = jwtService.generateJwt(username,Map.of(),LocalDateTime.now().minusHours(1));

        boolean result = jwtService.isTokenValid(token, username);

        assertFalse(result);

    }

    @Test
    public void shouldReturnFalseWhenInvalidToken(){

        String token = "this is token for test";

        boolean result = jwtService.isTokenValid(token, "ahmed@gmail.com");
        
        assertFalse(result);

    }

    @Test
    public void shouldReturnFalseWhenTokenModified(){

        String token = jwtService.generateJwt("ahmed@gmail.com", Map.of(), LocalDateTime.now().plusHours(1));

        String temptedToken = token.substring(0,token.length()-3)+ "xx";

        boolean result =  jwtService.isTokenValid(temptedToken, "ahmed@gmail.com");

        assertFalse(result);

    }


}
