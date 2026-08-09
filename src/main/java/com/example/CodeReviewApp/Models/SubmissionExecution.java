package com.example.CodeReviewApp.Models;


import java.time.LocalDateTime;

import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;

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
public class SubmissionExecution {
    private Long id;

    private Long submission_id;

    private SubmissionExecutionStatus status;
    
    private String error_message;

    private LocalDateTime started_at;
    private LocalDateTime  completed_at ;

    
}
