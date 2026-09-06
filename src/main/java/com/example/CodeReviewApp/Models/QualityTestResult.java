package com.example.CodeReviewApp.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QualityTestResult {
    
    private Long executionId;
    private Integer bugs;
    private Integer vulnerabilities;
    private Integer codeSmells;
    private Integer coverage;
}
