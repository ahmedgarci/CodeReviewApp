package com.example.CodeReviewApp.util.Files.Validations;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ExtensionValidator implements IValidate{

    
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "java", "js", "ts", "py", "cpp", "c", "cs",
            "kt", "go", "rs", "sql", "xml", "json",
            "yml", "yaml", "properties", "md", "txt");

    @Override
    public void validate(List<MultipartFile> files) {

        for(MultipartFile file : files){

            String extension = getFileExtension(file.getOriginalFilename());

            if(!ALLOWED_EXTENSIONS.contains(extension)) throw new IllegalArgumentException(" extension not supported");

        }

    }

    private String getFileExtension(String fileName){
        int index = fileName.lastIndexOf('.');
        if(index == -1 ) throw new IllegalArgumentException("no file extension");

        return fileName.substring(index+1);

    }
    
}
