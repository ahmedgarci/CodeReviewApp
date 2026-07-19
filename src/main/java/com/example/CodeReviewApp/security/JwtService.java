package com.example.CodeReviewApp.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY="YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYQ==";

    public String generateJwt(String username, Map<String,Object> claims,LocalDateTime expiresAt){

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(generateSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token,String username){
        
        try {

            Claims claims = extractAllClaims(token);

            if(!claims.getSubject().equals(username)) return false;
        
            if(claims.getExpiration().before(new Date())) return false;

            return true;
        } catch (Exception e) {
            return false;
        }
       

    }

    private Claims extractAllClaims(String token){

        return Jwts.parser()

        .verifyWith(generateSecretKey())

        .build()

        .parseSignedClaims(token)

        .getPayload();

    }

    public String getSubject(String token){

        return extractAllClaims(token).getSubject();
        
    }


    private SecretKey generateSecretKey(){

        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(bytes);
    }

  

}
