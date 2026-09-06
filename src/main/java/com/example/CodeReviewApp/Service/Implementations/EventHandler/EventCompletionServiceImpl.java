package com.example.CodeReviewApp.Service.Implementations.EventHandler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CodeReviewApp.Models.ProjectQualitySettings;
import com.example.CodeReviewApp.Models.QualityTestResult;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;
import com.example.CodeReviewApp.Repo.ProjectQualityGateSettingsRepository;
import com.example.CodeReviewApp.Repo.SonarQualityResultsRepo;
import com.example.CodeReviewApp.Repo.SubmissionExecutionRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Repo.UserRepository;
import com.example.CodeReviewApp.Service.EventCompletionService;
import com.example.CodeReviewApp.Service.NotificationService;
import com.example.CodeReviewApp.mapper.SonarQualityResultFactory;
import com.example.CodeReviewApp.util.Listener.In.ReviewCompletedEvent;
import com.example.CodeReviewApp.util.Listener.In.ReviewFailureEvent;
import com.example.CodeReviewApp.util.Listener.In.SonarMetric;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EventCompletionServiceImpl implements EventCompletionService{
    
    private final SubmissionRepository submissionRepository;
    private final SubmissionExecutionRepository submissionExecutionRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final ProjectQualityGateSettingsRepository projectSettingsRepository;
    private final SonarQualityResultsRepo sonarQualityResultsRepo;
    private final SonarQualityResultFactory qualityResultFactory;
    private final SubmissionValidationService submissionValidationService;
    private final IssueService issueService;
    private final QualityGateService qualityGateService;

    @Override
    @Transactional
    public void handleSuccess(ReviewCompletedEvent event) {

        Submission submission = submissionRepository.getSubmission(event.getSubmissionId());

        submissionValidationService.validateSubmissionProjectId(event, submission);

        SubmissionExecution executionProcessEntity = submissionExecutionRepository.getSubmissionExecution(event.getExecutionId());

        submissionValidationService.validateExecutionProcess(executionProcessEntity, submission);

        boolean isValid = submissionValidationService.validateExecutionProcessStatus(executionProcessEntity);
        
        if(!isValid) return;

        issueService.saveIssues(event, submission);

        ProjectQualitySettings projectSettings = projectSettingsRepository.getSettingsByProjectId(submission.getProject_id());

        SonarMetric metrics = event.getMetric();  
        
        qualityGateService.saveQualityGateResult(metrics, projectSettings, executionProcessEntity.getId());        

        QualityTestResult qualityResult = qualityResultFactory.toQualityTestResult(metrics, executionProcessEntity.getId());

        sonarQualityResultsRepo.insert(qualityResult);        

        String toUserEmail = userRepository.getUserEmailById(submission.getSubmitter());

        notificationService.sendNotification(toUserEmail ,submission.getSubmitter());
    }


    // to correct this method
    @Override
    public void handleFailure(ReviewFailureEvent event) {
        SubmissionExecution execution = submissionExecutionRepository.getSubmissionExecution(event.getExecutionId());


    if (execution.getStatus() != SubmissionExecutionStatus.PENDING) {
        return; 
    }


    submissionExecutionRepository.updateFailure(execution.getId(),SubmissionExecutionStatus.FAILED,event.getErrorMessage());


    }
    
    
}
