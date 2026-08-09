package com.example.CodeReviewApp.util.Listener.In;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCompletedEvent {

    Long submissionId;
    Long projectId;
    Long executionId;
    List<SonarIssue> issues;
}
