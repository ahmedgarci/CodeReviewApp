package com.example.CodeReviewApp.Service;

import java.util.List;


import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.dto.Submissions.Out.FileContentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;

public interface SubmissionDetailsService {
    
    List<ProjectSubmissionsDto> getProjectSubmissions(Long projectId);

    SubmissionDetailsDto getSubmissionDetails(Long submissionId);

    FileContentResponseDto getFileContent(Long submissionId,Long projectId);

    void updateSubmissionStatus(Long submissionId,SubmissionStatus target);

}
