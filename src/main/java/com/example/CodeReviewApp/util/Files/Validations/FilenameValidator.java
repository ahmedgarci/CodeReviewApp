package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FilenameValidator implements IValidate{

    @Override
    public void validate(List<MultipartFile> files) {

    for(MultipartFile file : files){

        String filename = file.getOriginalFilename();

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Invalid filename.");
        }

        if (filename.contains("..") || filename.contains("/")  || filename.contains("\\")) {

            throw new IllegalArgumentException( "Invalid filename: " + filename);
        }
        
    }

    }
    
}
