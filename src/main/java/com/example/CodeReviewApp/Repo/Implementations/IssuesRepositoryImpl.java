package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.ISSUES;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Insert;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Repo.IssuesRepository;
import com.example.CodeReviewApp.util.Listener.In.SonarIssue;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IssuesRepositoryImpl implements IssuesRepository{

    private final DSLContext dsl;

    @Override
    public void insert(List<SonarIssue> issues, Long submissionId) {
    
        List<Insert<?>> queries = issues.stream()
            .<Insert<?>>map(issue -> dsl.insertInto(ISSUES)
                .columns(ISSUES.SUBMISSION_ID, ISSUES.SEVERITY, ISSUES.LINE_NUMBER,
                         ISSUES.FILE_NAME, ISSUES.MESSAGE)
                .values(submissionId, issue.getSeverity(), issue.getLine(),
                        issue.getComponent(), issue.getMessage()))
            .toList();
    
        dsl.batch(queries).execute();
    }
    
}
