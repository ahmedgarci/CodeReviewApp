package com.example.CodeReviewApp.dto.Project.Out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetails {
    
    private Long id;

    private String name;

    private String url;

    private String owner_name;

}
