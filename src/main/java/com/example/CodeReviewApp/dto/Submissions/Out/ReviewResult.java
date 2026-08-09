package com.example.CodeReviewApp.dto.Submissions.Out;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResult {

    private String message;

    private List<Long> reviewerIds;

} 
