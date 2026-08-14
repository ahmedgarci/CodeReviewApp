package com.example.CodeReviewApp.dto.Comments.In;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCommentDto(
    @Positive
    @NotNull
    Long line_number,

    @NotBlank
    String content
) {
    
}
