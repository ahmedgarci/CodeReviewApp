package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.Service.ProjectSettingsService;
import com.example.CodeReviewApp.dto.Project.In.ProjectSettingsDto;
import com.example.CodeReviewApp.dto.Project.Out.ProjectQualitySettingsDto;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/settings")
@RestController
@RequiredArgsConstructor
public class ProjectSettingsPresentation {
    
    private final ProjectSettingsService settingsService;

    
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectQualitySettingsDto> getProjectSettings( @Positive @PathVariable(required = true)  Long projectId) {

        return  ResponseEntity.ok(settingsService.getProjectSettings(projectId));

    }

    @PostMapping("/{projectId}")
    public ResponseEntity<Void> setProjectSettings(@RequestBody ProjectSettingsDto settings,@Positive @PathVariable(required = true)  Long projectId) {

        settingsService.setProjectSettings(projectId, settings);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{projectId}/enable")
    public ResponseEntity<Void> activateProjectSettings(@Positive @PathVariable(required = true)  Long projectId) {

        settingsService.enableSettings(projectId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{projectId}/disable")
    public ResponseEntity<Void> deactivateProjectSettings(@Positive @PathVariable(required = true)  Long projectId) {

        settingsService.disableSettings(projectId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    
    
}
