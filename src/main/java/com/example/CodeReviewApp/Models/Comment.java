package com.example.CodeReviewApp.Models;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    
    private Integer id;

    private Long author_id;

    private Long code_id;

    private Long submission_id;

    private String content;

    private Long line_number;

    private LocalDateTime created_at;
}
