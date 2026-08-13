package com.example.CodeReviewApp.Service.ReviewsStrategy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.ReviewAssigneesRepository;
import com.example.CodeReviewApp.dto.Submissions.In.ReviewContext;
import com.example.CodeReviewApp.dto.Submissions.Out.ReviewResult;
import com.example.CodeReviewApp.exceptions.BadCredentials;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HumanReviewService implements ReviewStrategy{

    private final ProjectMembersRepository projectMembersRepository;
    private final ReviewAssigneesRepository reviewAssigneesRepository;

    @Override
    public ReviewResult execute(ReviewContext context) {

        List<Long> reviewers = context.getReviewers().stream().distinct().toList();

        if(reviewers == null || reviewers.isEmpty()) throw new BadCredentials("At least one reviewer is required");

        if(!projectMembersRepository.reviewersAreProjectMembers(context.getProjectId(),reviewers)) {

            throw new RessourceNotFoundException("Invalid reviewers");
        
        }
        reviewAssigneesRepository.insert(context.getSubmissionId(),reviewers);

        return new ReviewResult("Reviewers assigned",reviewers);

    }
    

}
