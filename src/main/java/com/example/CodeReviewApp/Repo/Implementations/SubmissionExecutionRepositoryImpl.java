package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.tables.SubmissionExecution.SUBMISSION_EXECUTION;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.Enums.SubmissionExecutionStatus;
import com.example.CodeReviewApp.Repo.SubmissionExecutionRepository;
import com.example.jooq.tables.records.SubmissionExecutionRecord;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubmissionExecutionRepositoryImpl implements SubmissionExecutionRepository {

    private final DSLContext dsl;

   @Override
    public Long insert(SubmissionExecution submission) {

    SubmissionExecutionRecord result = dsl.insertInto(SUBMISSION_EXECUTION)
        .columns(SUBMISSION_EXECUTION.SUBMISSION_ID, SUBMISSION_EXECUTION.STATUS, SUBMISSION_EXECUTION.STARTED_AT)
        .values(submission.getSubmission_id(), submission.getStatus().name(), submission.getStarted_at())
        .returning(SUBMISSION_EXECUTION.ID)
        .fetchOne();

    return result.getValue(SUBMISSION_EXECUTION.ID);
}

    @Override
    public void updateStatus(Long executionId, SubmissionExecutionStatus target) {

        dsl.update(SUBMISSION_EXECUTION).set(SUBMISSION_EXECUTION.STATUS,target.name()).where(SUBMISSION_EXECUTION.ID.eq(executionId));

    }

    @Override
    public SubmissionExecution getSubmissionExecution(Long id) {

        return dsl.select(SUBMISSION_EXECUTION.fields()).from(SUBMISSION_EXECUTION).where(SUBMISSION_EXECUTION.ID.eq(id)).fetchOneInto(SubmissionExecution.class);

    }

    @Override
    public void updateFailure(Long executionId, SubmissionExecutionStatus target, String cause) {

        dsl.update(SUBMISSION_EXECUTION).set(SUBMISSION_EXECUTION.STATUS,target.name()).set(SUBMISSION_EXECUTION.ERROR_MESSAGE,cause).where(SUBMISSION_EXECUTION.ID.eq(executionId)).execute();

    }

    
    
}
