package com.example.CodeReviewApp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemebers {
    
    private Long id;
    private Long project_id;
    private Long user_id;

}
