package com.example.CodeReviewApp.Models;

import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    private Long id;
    private Long submitter;
    private String title;
    private Long project_id;
    private String description;
    private SubmissionStatus status;
}
