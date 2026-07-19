package com.example.CodeReviewApp.Service;

import java.util.List;

import com.example.CodeReviewApp.dto.Project.In.CreateProjectDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;
import com.example.CodeReviewApp.dto.User.UserDto;

public interface ProjectService {

    void createProject(CreateProjectDto createProjectDto);

    List<UserDto> getProjectCollaborators(Long project_id);

    List<ProjectDetails> getUserInvolvedProjects();
    
}
