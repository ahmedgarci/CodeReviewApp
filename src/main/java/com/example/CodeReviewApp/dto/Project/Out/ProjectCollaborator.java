package com.example.CodeReviewApp.dto.Project.Out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectCollaborator {

    private String name;
    
    private String email;
}
