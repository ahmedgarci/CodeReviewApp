package com.example.CodeReviewApp.mapper;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.dto.Project.In.CreateProjectDto;

@Component
public class ProjecFactory {
    
    public Project toProject(CreateProjectDto createProjectDto){

        return Project.builder()
                      .name(createProjectDto.name())
                      .url(createProjectDto.url())
                      .build();
    }
}
