package com.example.CodeReviewApp.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.dto.Submissions.In.PublishReviewDto;
import com.example.CodeReviewApp.dto.Submissions.Out.FileContentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;

public interface SubmissionService {
    
    void publishCode(PublishReviewDto dto,List<MultipartFile> files,Long projectId);

    List<ProjectSubmissionsDto> getProjectSubmissions(Long projectId);

    SubmissionDetailsDto getSubmissionDetails(Long submissionId);

    FileContentResponseDto getFileContent(Long submissionId,Long projectId);

    void updateSubmissionStatus(Long submissionId,SubmissionStatus target);

}
