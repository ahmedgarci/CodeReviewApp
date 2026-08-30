package com.example.CodeReviewApp.mapper;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.ProjectQualitySettings;
import com.example.CodeReviewApp.dto.Project.In.ProjectSettingsDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectQualitySettingsDto;

@Component
public class ProjectSettingsFactory {
    
    public ProjectQualitySettings create(Long projectId,ProjectSettingsDto settings){

        return ProjectQualitySettings.builder().max_bugs(settings.maxBugs()).max_code_smells(settings.maxCodeSmells()).max_vulnerabilities(settings.maxVulnerabilities())
        .min_coverage(settings.minCoverage()).project_id(projectId)
        .build();
    }

    public ProjectQualitySettingsDto toProjectSettingsDto(ProjectQualitySettings settings){

        return new ProjectQualitySettingsDto(settings.getMax_bugs(),settings.getMax_vulnerabilities(),settings.getMax_code_smells(),settings.getMin_coverage(),settings.isEnabled());

    }
}
