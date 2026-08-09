package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.util.Listener.In.SonarIssue;

public interface IssuesRepository {

    void insert(List<SonarIssue> issues,Long submissionId);
}
