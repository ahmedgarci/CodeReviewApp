package com.example.CodeReviewApp.util.Listener.In;

public record SonarMetric(
    Long bugs,
    Long vulnerabilities,
    Long codeSmells,
    Double coverage
) {
    
}
