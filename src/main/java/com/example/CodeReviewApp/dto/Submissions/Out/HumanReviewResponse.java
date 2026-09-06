package com.example.CodeReviewApp.dto.Submissions.Out;

import java.util.List;

import com.example.CodeReviewApp.dto.File.FileDto;
import com.example.CodeReviewApp.dto.User.UserDto;

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
public class HumanReviewResponse {
    
        private String title;
    
        private String description;
    
        private UserDto author;
    
        private List<FileDto> files;
    
        private List<UserDto> reviewers;

        private String status;

        private List<String> labels;
        
    
}
