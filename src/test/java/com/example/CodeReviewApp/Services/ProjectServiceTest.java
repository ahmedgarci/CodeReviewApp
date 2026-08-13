package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.ProjectRepository;
import com.example.CodeReviewApp.Service.Implementations.ProjectServiceImpl;
import com.example.CodeReviewApp.dto.Project.In.CreateProjectDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;
import com.example.CodeReviewApp.mapper.ProjecFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private  ProjectRepository projectRepository;
    @Mock
    private  ProjecFactory projecFactory;
    @Mock
    private  AuthenticationContext authenticationContext;
    @Mock
    private  ProjectMembersRepository projectMembersRepository;

    @InjectMocks
    private ProjectServiceImpl projectServiceImpl;

    @Test
    public void shouldCreateProject(){
        
        CreateProjectDto dto = new CreateProjectDto("name", "url");

        Project project = new Project();

        User user = new User();

        user.setId(10L);
        
        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(projecFactory.toProject(dto)).thenReturn(project);

        projectServiceImpl.createProject(dto);

        verify(projecFactory).toProject(dto);

        verify(authenticationContext).getCurrentUser();

        verify(projectRepository).insertProject(project);

        assertEquals(10L, user.getId());

    }

    @Test
    public void shouldReturnUserInvolvedProjects(){

        List<ProjectDetails> projectDetails = new ArrayList<>();

        projectDetails.add(new ProjectDetails());

        User user = new User();

        user.setId(1L);

        when(authenticationContext.getCurrentUser()).thenReturn(user);
    
        when(projectRepository.getUserInvolvedProjects(user.getId())).thenReturn(projectDetails);

        List<ProjectDetails> result = projectServiceImpl.getUserInvolvedProjects();

        verify(authenticationContext).getCurrentUser();

        verify(projectRepository).getUserInvolvedProjects(user.getId());

        assertEquals(projectDetails, result);
    }

    @Test
    public void shouldReturnWhenUserNotAuthenticatedInInvolvedProjects(){

        when(authenticationContext.getCurrentUser()).thenReturn(null);
    
        assertThrows(NullPointerException.class,() -> projectServiceImpl.getUserInvolvedProjects());    
        
        verify(authenticationContext).getCurrentUser();

        verify(projectRepository,never()).getUserInvolvedProjects(anyLong());
    
    }


    


    
}
