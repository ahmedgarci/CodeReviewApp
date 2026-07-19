package com.example.CodeReviewApp.util.Files.Validations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DuplicateNameValidator implements IValidate{


    @Override
    public void validate(List<MultipartFile> files) {
        Set<String> filesNames = new HashSet<>();

        for(MultipartFile file: files){
            String filename = file.getOriginalFilename();

            if (!filesNames.add(filename)) {
                throw new IllegalArgumentException("Duplicate filename: " + filename);
            }

        }
        

    }
    
}
