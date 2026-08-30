package com.example.CodeReviewApp.Service;

import com.example.CodeReviewApp.dto.Project.In.ProjectSettingsDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectQualitySettingsDto;

public interface ProjectSettingsService {
    
    ProjectQualitySettingsDto getProjectSettings(Long projectId);

    void setProjectSettings(Long projectId,ProjectSettingsDto settingsDto);
    
    void enableSettings(Long projectId);
    
    void disableSettings(Long projectId);
}
