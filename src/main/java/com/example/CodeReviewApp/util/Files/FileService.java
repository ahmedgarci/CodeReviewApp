package com.example.CodeReviewApp.util.Files;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Models.CodeFile;

public interface FileService {

    List<CodeFile> uploadFiles(Long submissionId, List<MultipartFile> files);

    byte[] readFile(String filePath);
    
    void deleteSubmissionFiles(Long submissionId);

    void moveFilesToPermanentStorage(Long submissionId);

    String resolveSubmissionUploadDir(Long submissionId);
}