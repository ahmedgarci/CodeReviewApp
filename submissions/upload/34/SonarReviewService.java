package com.example.CodeReviewApp.Service.ReviewsStrategy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.dto.Submissions.In.ReviewContext;
import com.example.CodeReviewApp.dto.Submissions.Out.ReviewResult;
import com.example.CodeReviewApp.util.Files.FileService;
import com.example.CodeReviewApp.util.SonarQube.IProcessBuilder;
import com.example.CodeReviewApp.util.SonarQube.JavaCompilerService;
import com.example.CodeReviewApp.util.SonarQube.SonarResultRetriverService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SonarReviewService implements ReviewStrategy{


    private final IProcessBuilder processBuilder;
    private final SonarResultRetriverService sonarResultService;
    private final FileService fileService;
    private final JavaCompilerService javaCompilerService;

    @Override
    public ReviewResult execute(ReviewContext context) {
        try {

            String path = fileService.resolveSubmissionUploadDir(context.getSubmissionId());

            System.out.println("START COMPILATION");

            javaCompilerService.compile(path);

            System.out.println("COMPILATION DONE");

            processBuilder.execute(path);

            String issues =  sonarResultService.getIssues("temp-project");

            return new ReviewResult(issues, List.of());
            
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}  
