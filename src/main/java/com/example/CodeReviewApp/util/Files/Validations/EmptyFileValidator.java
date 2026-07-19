package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class EmptyFileValidator implements IValidate {

    @Override
    public void validate(List<MultipartFile> files) {
        for(MultipartFile file : files){
            if (file.isEmpty()) {
                throw new IllegalArgumentException(file.getOriginalFilename() + " is empty.");
            }
        }
       
    }
    
}
