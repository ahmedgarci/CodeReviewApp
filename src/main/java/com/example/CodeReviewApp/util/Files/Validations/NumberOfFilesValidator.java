package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class NumberOfFilesValidator implements IValidate {

    private static final int MAX_FILES = 20;

    @Override
    public void validate(List<MultipartFile> files) {
        if (files.size() > MAX_FILES) {
            throw new IllegalArgumentException("Maximum " + MAX_FILES + " files are allowed.");
        }
    }
    
}
