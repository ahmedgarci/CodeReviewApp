package com.example.CodeReviewApp.dto.Comments.OUt;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    
        
    private Integer id;

    private String author;

    private String content;

    private Long line_number;

    private LocalDateTime created_at;
}
