package com.example.CodeReviewApp.dto.Project.In;


public record ProjectSettingsDto(
    Long maxBugs,
    Long maxVulnerabilities ,
    Long maxCodeSmells,
    Long minCoverage
) {  
} 
