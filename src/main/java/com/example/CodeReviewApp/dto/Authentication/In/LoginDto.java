package com.example.CodeReviewApp.dto.Authentication.In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
    @Email
    @NotBlank
    String email,
    
    @Size(min = 8)
    String password
) {
    
}
