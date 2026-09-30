package com.example.CodeReviewApp.Service.Implementations.EventHandler;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Repo.IssuesRepository;
import com.example.CodeReviewApp.util.Listener.In.ReviewCompletedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class IssueService {
    
    private final IssuesRepository issuesRepository;

    public void saveIssues(ReviewCompletedEvent event,Submission submission) {
        if (event.getIssues() != null && !event.getIssues().isEmpty()) {
            System.out.println("Saving issues for submission: " + submission.getId());            
            issuesRepository.insert(event.getIssues(), submission.getId());
            
        }
    }
}
