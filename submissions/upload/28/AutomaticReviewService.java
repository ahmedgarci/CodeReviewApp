package com.example.CodeReviewApp.Service.ReviewsStrategy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.ReviewAssigneesRepository;
import com.example.CodeReviewApp.dto.Submissions.In.ReviewContext;
import com.example.CodeReviewApp.dto.Submissions.Out.ReviewResult;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutomaticReviewService implements ReviewStrategy {

    private final AuthenticationContext authenticationContext;
    private final ProjectMembersRepository projectMembersRepository;
    private final ReviewAssigneesRepository reviewAssigneesRepository;

    @Override
    public ReviewResult execute(ReviewContext context) {

        User author = authenticationContext.getCurrentUser();

        User assignedTo = projectMembersRepository.getAvailableReviewer(context.getProjectId(), author.getId());

        if(assignedTo == null) throw new RuntimeException("no user found to review");

        reviewAssigneesRepository.insert(context.getSubmissionId(), List.of(assignedTo.getId()));

        return new ReviewResult();

    }
    
}
