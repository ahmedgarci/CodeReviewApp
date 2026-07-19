package com.example.CodeReviewApp.dto.Authentication.Out;

import java.time.LocalDateTime;

public record SuccessFulAuthentication(
    String username,
    String email,
    String token,
    LocalDateTime expiresAt
) {
    
}
