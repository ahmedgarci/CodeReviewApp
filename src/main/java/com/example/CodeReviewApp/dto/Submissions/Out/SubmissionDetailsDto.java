package com.example.CodeReviewApp.dto.Submissions.Out;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder 
public class SubmissionDetailsDto {

        
        private Long id;
    
        private LocalDateTime submitted_at;

        private SonarReviewResponse sonarReviewResponse;

        private HumanReviewResponse humanReviewResponse;

        private String reviewType;
}
