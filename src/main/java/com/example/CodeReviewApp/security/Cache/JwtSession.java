package com.example.CodeReviewApp.security.Cache;

import java.time.LocalDateTime;

public record JwtSession(String token,LocalDateTime expiresAt) {
    
}
