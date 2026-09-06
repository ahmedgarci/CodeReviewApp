package com.example.CodeReviewApp.Repo;

import com.example.CodeReviewApp.Models.QualityTestResult;

public interface SonarQualityResultsRepo {
    
    public void insert(QualityTestResult testResult);
    public QualityTestResult get(Long executionJobId);
}
