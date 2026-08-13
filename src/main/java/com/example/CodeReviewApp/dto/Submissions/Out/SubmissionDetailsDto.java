package com.example.CodeReviewApp.dto.Submissions.Out;

import java.time.LocalDateTime;
import java.util.List;

import com.example.CodeReviewApp.dto.File.FileDto;
import com.example.CodeReviewApp.dto.User.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDetailsDto {

    
        private Long id;
    
        private String title;
    
        private String description;
    
        private UserDto author;
    
        private List<FileDto> files;
    
        private List<UserDto> reviewers;

        private String status;

        private LocalDateTime submitted_at;

        private List<String> labels;

        private List<SonarIssue> sonarIssues;
}
