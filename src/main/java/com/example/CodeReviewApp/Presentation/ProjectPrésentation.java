package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.Service.ProjectService;
import com.example.CodeReviewApp.dto.Project.In.CreateProjectDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;
import com.example.CodeReviewApp.dto.User.UserDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectPrésentation {
    
    private final ProjectService projectService;
    
    
    @PostMapping()
    public ResponseEntity<?> insertProject(@RequestBody @Valid CreateProjectDto request) {

        projectService.createProject(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/{ProjectId}")
    public ResponseEntity<List<UserDto>> getProjectCollab(@PathVariable(name = "ProjectId") @Positive Long ProjectId) {

        return  ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectCollaborators(ProjectId));
        
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProjectDetails>> getUserProjects() {

        return ResponseEntity.status(HttpStatus.OK).body(projectService.getUserInvolvedProjects());

    }
    

    

}
