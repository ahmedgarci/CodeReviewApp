package com.example.CodeReviewApp.dto.Submissions.In;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewContext {


    private Long submissionId;

    private Long projectId;

    private List<Long> reviewers;

}
