package com.example.CodeReviewApp.util.Files;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.util.Files.Validations.IValidate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileValidationFacade {
    
    private final List<IValidate> validations;

    public void validate(List<MultipartFile> files){

        for(IValidate validator : validations){

            validator.validate(files);

        }

    }

}
