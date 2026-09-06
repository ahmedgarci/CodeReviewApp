package com.example.CodeReviewApp.Service.Implementations.EventHandler;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.ProjectQualitySettings;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;
import com.example.CodeReviewApp.Repo.SubmissionExecutionRepository;
import com.example.CodeReviewApp.util.Listener.In.SonarMetric;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class QualityGateService {

    private final SubmissionExecutionRepository submissionExecutionRepository;

    public void saveQualityGateResult(SonarMetric metrics, ProjectQualitySettings projectSettings,Long executionId) {

        if(projectSettings.isEnabled() && projectSettings.getMax_bugs() < metrics.bugs() || projectSettings.getMax_code_smells() < metrics.codeSmells()
         || projectSettings.getMax_vulnerabilities() < metrics.vulnerabilities() || projectSettings.getMin_coverage() < metrics.coverage()){
            
            submissionExecutionRepository.updateFailure(executionId, SubmissionExecutionStatus.FAILED, "tests failed because of the project settings are enabled");

        }else{

            submissionExecutionRepository.updateStatus(executionId, SubmissionExecutionStatus.COMPLETED);

        }


    }
}
