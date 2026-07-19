package com.example.CodeReviewApp.util.Auth;

import java.time.LocalDateTime;

public record AuthenticationSession(
    String token,
    LocalDateTime expiresAt
) {
    
}
