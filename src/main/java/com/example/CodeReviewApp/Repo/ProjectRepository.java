package com.example.CodeReviewApp.Repo;


import java.util.List;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;

public interface ProjectRepository {
    
    void insertProject(Project project);

    List<ProjectDetails> getUserInvolvedProjects(Long user_id);

    Project getProjectById(Long projectId);
}
