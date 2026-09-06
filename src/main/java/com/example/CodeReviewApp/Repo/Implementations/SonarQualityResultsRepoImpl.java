package com.example.CodeReviewApp.Repo.Implementations;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.QualityTestResult;
import com.example.CodeReviewApp.Repo.SonarQualityResultsRepo;
import static com.example.jooq.tables.ReviewResults.REVIEW_RESULTS;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SonarQualityResultsRepoImpl implements SonarQualityResultsRepo {
    
    private final DSLContext dsl;

    @Override
    public void insert(QualityTestResult testResult) {
        
        dsl.insertInto(REVIEW_RESULTS).columns(REVIEW_RESULTS.SUBMISSION_EXECUTION_ID,REVIEW_RESULTS.BUGS,REVIEW_RESULTS.CODE_SMELLS,REVIEW_RESULTS.VULNERABILITIES,REVIEW_RESULTS.COVERAGE)
        .values(testResult.getExecutionId(),testResult.getBugs(),testResult.getCodeSmells(),testResult.getVulnerabilities(),testResult.getCoverage()).execute();
    }

    @Override
    public QualityTestResult get(Long executionJobId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }


}
