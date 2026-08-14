package com.example.CodeReviewApp.dto.Project.In;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectDto(
    @NotBlank
    String name,

    @NotBlank
    String url

) {
} 
