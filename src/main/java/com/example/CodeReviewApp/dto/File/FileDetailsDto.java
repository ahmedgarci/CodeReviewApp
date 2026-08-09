package com.example.CodeReviewApp.dto.File;

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
public class FileDetailsDto {

    private Long id;

    private String filename;

    private String extension;

    private byte[] content;

    private Long size;
    
}
