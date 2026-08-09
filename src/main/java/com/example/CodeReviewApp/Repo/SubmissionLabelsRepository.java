package com.example.CodeReviewApp.Repo;

import java.util.List;

public interface SubmissionLabelsRepository {
    
    void assignLabelToSubmission(Long submissionId,List<Long> labels);

    List<String> getSubmissionLabels(Long submissionId);
}
