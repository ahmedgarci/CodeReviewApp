package com.example.CodeReviewApp.Service.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.ProjectRepository;
import com.example.CodeReviewApp.Service.ProjectService;
import com.example.CodeReviewApp.dto.Project.In.CreateProjectDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;
import com.example.CodeReviewApp.dto.User.UserDto;
import com.example.CodeReviewApp.mapper.ProjecFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjecFactory projecFactory;
    private final AuthenticationContext authenticationContext;
    private final ProjectMembersRepository projectMembersRepository;
    

    @Override
    @Transactional
    public void createProject(CreateProjectDto createProjectDto) {
        
        // check uniqueneess to avoid duplicates

        Project project = projecFactory.toProject(createProjectDto);

        project.setOwner_id(authenticationContext.getCurrentUser().getId());

        projectRepository.insertProject(project);

    }


    @Override
    public List<UserDto> getProjectCollaborators(Long project_id) {

        return  projectMembersRepository.getProjectCollaborators(project_id);

    }


    @Override
    public List<ProjectDetails> getUserInvolvedProjects() {

        User connectedUser = authenticationContext.getCurrentUser();

        return projectRepository.getUserInvolvedProjects(connectedUser.getId());
    }

    

    
    



    
}
