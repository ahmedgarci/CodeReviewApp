package com.example.CodeReviewApp.mapper;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.QualityTestResult;
import com.example.CodeReviewApp.util.Listener.In.SonarMetric;

@Component
public class SonarQualityResultFactory {
    
    public QualityTestResult toQualityTestResult(SonarMetric metric,Long execId){

        return QualityTestResult.builder().bugs(metric.bugs().intValue())
        .codeSmells(metric.codeSmells().intValue())
        .coverage(metric.coverage().intValue())
        .vulnerabilities(metric.vulnerabilities().intValue())
        .executionId(execId)
        .build();
    }
}
