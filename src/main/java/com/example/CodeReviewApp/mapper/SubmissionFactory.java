package com.example.CodeReviewApp.mapper;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.Enums.ReviewType;
import com.example.CodeReviewApp.dto.Submissions.In.PublishReviewDto;

@Component
public class SubmissionFactory {


    public Submission toSubmission(PublishReviewDto reviewDto,Long projectId){

        return Submission.builder()
                        .description(reviewDto.getDescription())
                        .review_type(ReviewType.HUMAN.name())
                        .title(reviewDto.getTitle())
                        .project_id(projectId)
        .build();

    }
    
}
