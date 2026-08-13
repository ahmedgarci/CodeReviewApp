package com.example.CodeReviewApp.Models;

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
public class Notification {

    private Long id;

    private Long userId;

    private String title;
    
    private String message ;

    private LocalDateTime sent_at;

    private String type;
   
}
