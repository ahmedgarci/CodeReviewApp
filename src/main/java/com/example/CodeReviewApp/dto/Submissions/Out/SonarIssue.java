package com.example.CodeReviewApp.dto.Submissions.Out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SonarIssue {

    private Long id;
    private String severity;
    private String fileName;
    private Long lineNumber;
    private String message;
}