package com.example.CodeReviewApp.dto.Project.Out;

public record ProjectQualitySettingsDto(
    Long max_bugs ,
    Long  max_vulnerabilities ,
    Long  max_code_smells ,
    Long min_coverage ,
    boolean enabled 
) {
    
}
