package com.example.CodeReviewApp.Service.Implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.Models.ProjectQualitySettings;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.ProjectQualityGateSettingsRepository;
import com.example.CodeReviewApp.Repo.ProjectRepository;
import com.example.CodeReviewApp.Service.ProjectSettingsService;
import com.example.CodeReviewApp.dto.Project.In.ProjectSettingsDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectQualitySettingsDto;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.mapper.ProjectSettingsFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectSettingsServiceImpl implements ProjectSettingsService{

    private final ProjectRepository projectRepository;
    private final ProjectQualityGateSettingsRepository projectQualityGateSettingsRepository;
    private final AuthenticationContext authenticationContext;
    private final ProjectSettingsFactory projectSettingsFactory;


    public ProjectQualitySettingsDto getProjectSettings(Long projectId){

        ProjectQualitySettings settings = Optional.ofNullable(projectQualityGateSettingsRepository.getSettingsByProjectId(projectId)).orElseThrow(()-> new RuntimeException("null"));

        return projectSettingsFactory.toProjectSettingsDto(settings);
    }

    public void setProjectSettings(Long projectId,ProjectSettingsDto settingsDto){

        Project project = Optional.ofNullable(projectRepository.getProjectById(projectId)).orElseThrow(()-> new RessourceNotFoundException("project was not found"));

        User connectedUser = authenticationContext.getCurrentUser();

        if(!connectedUser.getId().equals(project.getOwner_id())) throw new RuntimeException("null");    
        
        ProjectQualitySettings settings = projectSettingsFactory.create(projectId,settingsDto);

        projectQualityGateSettingsRepository.insert(settings);
        
    }

    public void enableSettings(Long projectId){

        ProjectQualitySettings project = Optional.ofNullable(projectQualityGateSettingsRepository.getSettingsByProjectId(projectId)).orElseThrow(()-> new RessourceNotFoundException("project was not found"));

        if(project.isEnabled()) return;

        projectQualityGateSettingsRepository.enable(projectId);
    }

    public void disableSettings(Long projectId){

        ProjectQualitySettings project = Optional.ofNullable(projectQualityGateSettingsRepository.getSettingsByProjectId(projectId)).orElseThrow(()-> new RessourceNotFoundException("project was not found"));

        if(!project.isEnabled()) return;

        projectQualityGateSettingsRepository.disable(projectId);

    }

}
