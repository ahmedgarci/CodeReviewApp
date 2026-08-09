package com.example.CodeReviewApp.Service.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Repo.CodeFileRepository;

import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Service.SubmissionCreationService;
import com.example.CodeReviewApp.Service.ReviewsStrategy.ReviewStrategy;
import com.example.CodeReviewApp.Service.ReviewsStrategy.ReviewStrategyResolver;
import com.example.CodeReviewApp.dto.Submissions.In.PublishReviewDto;
import com.example.CodeReviewApp.dto.Submissions.In.ReviewContext;
import com.example.CodeReviewApp.mapper.SubmissionFactory;
import com.example.CodeReviewApp.security.Cache.Idempotent;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;
import com.example.CodeReviewApp.util.Files.FileService;
import com.example.CodeReviewApp.util.Files.FileValidationFacade;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionCreationServiceImpl implements SubmissionCreationService{


    private final FileValidationFacade fileValidationFacade;
    private final FileService fileService;
    private final SubmissionRepository submissionRepository;
    private final CodeFileRepository codefFileRepository;
    private final AuthenticationContext authenticationContext;
    private final SubmissionFactory submissionFactory;
    private final ReviewStrategyResolver strategyResolver;


    @Override
    @Transactional
    @Idempotent
    public void publishCode(PublishReviewDto reviewDto,List<MultipartFile> files,Long projectId) {

        fileValidationFacade.validate(files);

        Long submissionId = null;

        try {
                
            Submission submission = submissionFactory.toSubmission(reviewDto, projectId);
    
            submission.setSubmitter(authenticationContext.getCurrentUser().getId());
    
            submissionId = submissionRepository.insertSubmission(submission);
            
            List<CodeFile> uploadedFiles = fileService.uploadFiles(submissionId, files);
    
            for(CodeFile file : uploadedFiles) codefFileRepository.insert(file);

            ReviewStrategy reviewStrategy = strategyResolver.resolve(reviewDto.getStrategy());

            reviewStrategy.execute(new ReviewContext(submissionId, projectId, reviewDto.getReviewers()));

            fileService.moveFilesToPermanentStorage(submissionId);
            
        } catch (Exception e) {

            if(submissionId != null){

                fileService.deleteSubmissionFiles(submissionId);

            }

            throw e;

        }    

    }
    
}
