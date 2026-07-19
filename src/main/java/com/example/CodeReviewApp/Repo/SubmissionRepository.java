package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;

public interface SubmissionRepository {

    Long insertSubmission(Submission submission);

    List<ProjectSubmissionsDto> getProjectSubmissions(Long projectId);

    SubmissionDetailsDto getSubmissionDetails(Long submissionId);

    Submission getSubmission(Long submissionId);

    void updateSubmissionStatus(Long submissionId,SubmissionStatus target);

    boolean isSubmissionClosed(Long submissionId);
    
}
