package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SizeValidator implements IValidate{

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; 

    @Override
    public void validate(List<MultipartFile> files) {

        for(MultipartFile file : files){
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException(
                        file.getOriginalFilename() + " exceeds the maximum allowed size.");
            }
        }

    }

}
