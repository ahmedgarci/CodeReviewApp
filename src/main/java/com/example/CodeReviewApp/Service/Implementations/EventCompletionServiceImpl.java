package com.example.CodeReviewApp.Service.Implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CodeReviewApp.Models.Notification;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;
import com.example.CodeReviewApp.Repo.IssuesRepository;
import com.example.CodeReviewApp.Repo.SubmissionExecutionRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Repo.UserRepository;
import com.example.CodeReviewApp.Service.EventCompletionService;
import com.example.CodeReviewApp.Service.NotificationService;
import com.example.CodeReviewApp.mapper.NotificationFactory;
import com.example.CodeReviewApp.util.Listener.In.ReviewCompletedEvent;
import com.example.CodeReviewApp.util.Listener.In.ReviewFailureEvent;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EventCompletionServiceImpl implements EventCompletionService{
    
    private final SubmissionRepository submissionRepository;
    private final IssuesRepository issuesRepository;
    private final SubmissionExecutionRepository submissionExecutionRepository;
    private final NotificationService notificationService;
    private final NotificationFactory notificationFactory;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void handleSuccess(ReviewCompletedEvent event) {

        Submission submission = submissionRepository.getSubmission(event.getSubmissionId());

        if(!submission.getProject_id().equals(event.getProjectId())) throw new IllegalStateException("invalid project ");

        SubmissionExecution executionProcessEntity = submissionExecutionRepository.getSubmissionExecution(event.getExecutionId());

        if(!executionProcessEntity.getSubmission_id().equals(submission.getId())) throw new   IllegalStateException("Invalid execution");

        if (executionProcessEntity.getStatus() != SubmissionExecutionStatus.PENDING) {
            return; 
        }
        
        if (event.getIssues() != null && !event.getIssues().isEmpty()) {
            
            issuesRepository.insert(event.getIssues(), submission.getId());
            
        }

        submissionExecutionRepository.updateStatus(executionProcessEntity.getId(), SubmissionExecutionStatus.COMPLETED);

        String to = userRepository.getUserEmailById(submission.getSubmitter());

        Notification notif = notificationFactory.create("Project analyzed successfully", "Project was analyzed Successfully with sonarQube", submission.getSubmitter());

        notificationService.sendNotification(notificationFactory.toNotificationResponse(notif), to);
    }

    @Override
    public void handleFailure(ReviewFailureEvent event) {
        SubmissionExecution execution = submissionExecutionRepository.getSubmissionExecution(event.getExecutionId());


    if (execution.getStatus() != SubmissionExecutionStatus.PENDING) {
        return; 
    }


    submissionExecutionRepository.updateFailure(execution.getId(),SubmissionExecutionStatus.FAILED,event.getErrorMessage());


    }
    
    
}
