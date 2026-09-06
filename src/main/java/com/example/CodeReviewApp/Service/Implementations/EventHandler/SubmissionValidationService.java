package com.example.CodeReviewApp.Service.Implementations.EventHandler;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;
import com.example.CodeReviewApp.util.Listener.In.ReviewCompletedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionValidationService {


    public void validateSubmissionProjectId(ReviewCompletedEvent event,Submission submission) {

        if(!submission.getProject_id().equals(event.getProjectId())) throw new IllegalStateException("invalid project ");

    }

    public void validateExecutionProcess(SubmissionExecution executionProcessEntity, Submission submission) {

        if(!executionProcessEntity.getSubmission_id().equals(submission.getId())) throw new   IllegalStateException("Invalid execution");

    }

    public boolean validateExecutionProcessStatus(SubmissionExecution executionProcessEntity ) {

        if (executionProcessEntity.getStatus() != SubmissionExecutionStatus.PENDING) {
            return false; 
        }

        return true;

    }





}

