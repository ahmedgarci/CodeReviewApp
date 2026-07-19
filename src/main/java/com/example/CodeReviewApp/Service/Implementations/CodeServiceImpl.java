package com.example.CodeReviewApp.Service.Implementations;
import com.example.CodeReviewApp.mapper.CodeFileFactory;

import com.example.CodeReviewApp.util.Files.FileService;
import com.example.CodeReviewApp.Models.CodeFile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Repo.CodeFileRepository;

import com.example.CodeReviewApp.Service.CodeService;
import com.example.CodeReviewApp.dto.File.FileDetailsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService{

    private final CodeFileRepository codeRepository;
    private final FileService fileService;
    private final CodeFileFactory codeFileFactory;

    @Override
    public List<FileDetailsDto> getFileDetails(Long submissionId) {

        List<CodeFile> files = codeRepository.findBySubmissionId(submissionId);

        List<FileDetailsDto> result = new ArrayList<>();

        for(CodeFile file : files){

            byte[] fileContent = fileService.readFile(file.getFile_path());

            result.add(codeFileFactory.toFileDetailsDto(file, fileContent));

        }

        return result;

    }
    
    

}
