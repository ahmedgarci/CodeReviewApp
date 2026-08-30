package com.example.CodeReviewApp.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectQualitySettings {
    private Long id; 
    private Long project_id;
    private Long max_bugs ;
    private Long  max_vulnerabilities ;
    private Long  max_code_smells ;
    private Long min_coverage ;
    private boolean enabled ;
    
}
