package com.example.CodeReviewApp.Service;

import java.util.List;

import com.example.CodeReviewApp.dto.File.FileDetailsDto;

public interface CodeService {
    
    List<FileDetailsDto> getFileDetails(Long submissionId);

}
