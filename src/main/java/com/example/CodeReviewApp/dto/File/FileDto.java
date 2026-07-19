package com.example.CodeReviewApp.dto.File;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    
    private Long id;

    private String filename;

    private Long size;

}
