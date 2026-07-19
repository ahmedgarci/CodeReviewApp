package com.example.CodeReviewApp.Repo;

import java.util.List;


public interface ReviewAssigneesRepository {
    
    void insert(Long submissionId,List<Long> reviewerIds);

    boolean isUserAllowedToReviewSubmission(Long submissionId,Long reviewerId);
}
