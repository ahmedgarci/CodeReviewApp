package com.example.CodeReviewApp.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.dto.File.FileDetailsDto;

@Component
public class CodeFileFactory {
    

    public CodeFile toCodeFile(MultipartFile file,String path){

        return CodeFile.builder()
                        .file_path(path)
                        .filename(file.getOriginalFilename())
                        .uploaded_at(LocalDateTime.now())
                        .size(file.getSize())
                        .build();
    }

    public FileDetailsDto toFileDetailsDto(CodeFile file,byte[] content){

        return FileDetailsDto.builder()
                .id(file.getId())
                .content(content)
                .filename(file.getFilename())
                .size(file.getSize())
                .build();

    }

}
