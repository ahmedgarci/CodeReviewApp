package com.example.CodeReviewApp.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;

@Component
public class SubmissionExecutionFactory {

    public SubmissionExecution create(Long submissionId){

        return SubmissionExecution.builder().submission_id(submissionId).started_at(LocalDateTime.now()).status(SubmissionExecutionStatus.PENDING).build();

    }
}
