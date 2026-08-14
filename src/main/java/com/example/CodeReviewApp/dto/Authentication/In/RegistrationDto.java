package com.example.CodeReviewApp.dto.Authentication.In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationDto(
    @NotBlank
    @Email
    String email,
    @NotBlank
    String username,
    @NotBlank
    @Size(min = 8)
    String password
) {
    
}
