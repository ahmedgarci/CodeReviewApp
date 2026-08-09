package com.example.CodeReviewApp.util.Producer.out;

public record Event(
    String url,Long projectId,Long submissionId,Long executionId
) {
    
}
