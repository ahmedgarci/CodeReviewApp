package com.example.CodeReviewApp.Presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import com.example.CodeReviewApp.Service.ProjectService;
import com.example.CodeReviewApp.dto.Project.In.CreateProjectDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;
import com.example.CodeReviewApp.dto.User.UserDto;
import com.example.CodeReviewApp.security.JwtService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(ProjectPrésentation.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectPresentationTest {
    
    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    public void shouldCreateProject()throws Exception{

        mockMvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON).content("""
            {
                "name": "My Project",
                "url": "Test project"
            }
            """)).andExpect(status().isCreated());
        
        verify(projectService).createProject(any(CreateProjectDto.class));

    }

    @Test
    public void shouldReturn400WhenCreateProjectJsonIsInvalid()throws Exception{

        mockMvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON).content("""
            {
                "name": "",
                "description": ""
            }
            """)).andExpect(status().isBadRequest());
        
            verify(projectService,never()).createProject(any());
    }

    @Test
    public void shouldGetProjectCollaborators() throws Exception{

        Long projectId = 10L;
    
        UserDto user1 = new UserDto();
    
        UserDto user2 = new UserDto();

        List<UserDto> collaborators = List.of(user1, user2);

        when(projectService.getProjectCollaborators(projectId)).thenReturn(collaborators);

        mockMvc.perform(get("/project/{ProjectId}", projectId)).andExpect(status().isOk());

        verify(projectService).getProjectCollaborators(projectId);

    }

    @Test
    void shouldReturn400WhenProjectIdIsNotANumber() throws Exception {

        mockMvc.perform(get("/project/abc")).andExpect(status().isBadRequest());

        verify(projectService, never()).getProjectCollaborators(anyLong());
    
    }

    @Test
    void shouldReturn400WhenProjectIdIsNotAValidId() throws Exception {

        mockMvc.perform(get("/project/-1")).andExpect(status().isBadRequest());

        verify(projectService, never()).getProjectCollaborators(anyLong());
    
    }




    @Test
    public  void shouldGetUserProjects()throws Exception{

        List<ProjectDetails> projectDetails = List.of(new ProjectDetails());

        when(projectService.getUserInvolvedProjects()).thenReturn(projectDetails);

        mockMvc.perform(get("/project/me")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(1));

        verify(projectService).getUserInvolvedProjects();

    }



}
