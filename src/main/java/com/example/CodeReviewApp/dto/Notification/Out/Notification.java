package com.example.CodeReviewApp.dto.Notification.Out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Notification {
    
    private String id;
    private String title;
    private String message;
    private String time;
}
