package com.example.CodeReviewApp.Service.ReviewsStrategy;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Service.ReviewsStrategy.Enums.ReviewType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewStrategyResolver {

    private final HumanReviewService humanReviewService;
    public ReviewStrategy resolve(ReviewType  strategy){
        return switch (strategy) {
            case HUMAN -> humanReviewService;
            case AUTOMATIC -> humanReviewService;
        };        
    }
}
