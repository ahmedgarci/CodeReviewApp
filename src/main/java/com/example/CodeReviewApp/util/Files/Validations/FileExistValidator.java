package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileExistValidator  implements IValidate{

    @Override
    public void validate(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("At least one file must be uploaded.");
        }
    }
    
}
