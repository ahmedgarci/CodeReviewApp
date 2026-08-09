package com.example.CodeReviewApp.Service.ReviewsStrategy;

import com.example.CodeReviewApp.dto.Submissions.In.ReviewContext;
import com.example.CodeReviewApp.dto.Submissions.Out.ReviewResult;

public interface ReviewStrategy {

    ReviewResult execute(ReviewContext context);

} 
