package com.example.CodeReviewApp.Repo;

import com.example.CodeReviewApp.Models.ProjectQualitySettings;

public interface ProjectQualityGateSettingsRepository {
    
    void insert(ProjectQualitySettings projectSettings);
    ProjectQualitySettings getSettingsByProjectId(Long projectId);
    void disable(Long projectId);
    void enable(Long projectId);
    void modify(Long projectId);
}
