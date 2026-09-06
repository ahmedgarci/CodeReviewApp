package com.example.CodeReviewApp.dto.Submissions.Out;

import java.util.List;

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
public class SonarReviewResponse {
    
    private List<SonarIssue> sonarIssues;

    private QualityTestResultDto qualityTestResult;
}
