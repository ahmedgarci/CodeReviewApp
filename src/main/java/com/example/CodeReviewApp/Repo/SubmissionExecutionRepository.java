package com.example.CodeReviewApp.Repo;

import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;

public interface SubmissionExecutionRepository {
    
    void insert(SubmissionExecution submission);

    void updateStatus(Long submissionExecutionId,SubmissionExecutionStatus target);

    SubmissionExecution getSubmissionExecution(Long id);    

    void updateFailure(Long submissionExecutionId,SubmissionExecutionStatus target,String cause);

}
