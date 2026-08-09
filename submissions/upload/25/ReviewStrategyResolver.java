package com.example.CodeReviewApp.Service.ReviewsStrategy;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Service.ReviewsStrategy.Enums.ReviewType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewStrategyResolver {

    private final HumanReviewService humanReviewService;
    private final SonarReviewService sonarReviewService;
    public ReviewStrategy resolve(ReviewType  strategy){
        return switch (strategy) {
            case HUMAN -> humanReviewService;
            case AUTOMATIC -> humanReviewService;
            case SOONAR -> sonarReviewService;
        };        
    }
}
