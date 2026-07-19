package com.example.CodeReviewApp.Models;

import java.time.LocalDateTime;

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
public class CodeFile {

    private Long id;
    private Long submission_id;
    private String filename;
    private String file_path;
    private Long size;
    private LocalDateTime uploaded_at;
    
}
