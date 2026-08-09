package com.example.CodeReviewApp.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.dto.Submissions.In.PublishReviewDto;

public interface SubmissionCreationService {

    void publishCode(PublishReviewDto dto,List<MultipartFile> files,Long projectId);
    
}
