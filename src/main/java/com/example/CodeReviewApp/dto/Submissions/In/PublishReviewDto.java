package com.example.CodeReviewApp.dto.Submissions.In;

import java.util.List;

import com.example.CodeReviewApp.Service.ReviewsStrategy.Enums.ReviewType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishReviewDto {
    
    private String description;

    private String title;

    private List<Long> reviewers;

    private ReviewType strategy;
}
