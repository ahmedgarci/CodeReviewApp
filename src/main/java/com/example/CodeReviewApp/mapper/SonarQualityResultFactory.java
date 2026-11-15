package com.example.CodeReviewApp.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.QualityTestResult;
import com.example.CodeReviewApp.util.Listener.In.SonarMetric;

@Component
public class SonarQualityResultFactory {
    
    public QualityTestResult toQualityTestResult(SonarMetric metric,Long execId){
        
        return QualityTestResult.builder()
                .bugs(Objects.requireNonNullElse(metric.bugs(), 0L).intValue())
                .codeSmells(Objects.requireNonNullElse(metric.codeSmells(), 0L).intValue())
                .coverage(Objects.requireNonNullElse(metric.coverage(), 0L).intValue())
                .vulnerabilities(Objects.requireNonNullElse(metric.vulnerabilities(), 0L).intValue())
                .executionId(execId)
                .build();
    }
}
