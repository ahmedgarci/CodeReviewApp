package com.example.CodeReviewApp.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private Long id;
    private String name;
    private String url;
    private Long owner_id ;
    
}
