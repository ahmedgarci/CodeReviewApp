package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IValidate {
    
        void validate(List<MultipartFile> files);

}
