package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.LABELS;
import static com.example.jooq.Tables.SUBMISSION_LABELS;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Repo.SubmissionLabelsRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubmissionLabelRepositoryImpl implements SubmissionLabelsRepository {

    private final DSLContext dsl;

    @Override
    public void assignLabelToSubmission(Long submissionId, List<Long> labels) {
        Query[] queries = labels.stream()
        .map(label -> dsl.insertInto(SUBMISSION_LABELS).columns(SUBMISSION_LABELS.SUBMISSION_ID,SUBMISSION_LABELS.LABEL_ID).values(submissionId,label))
        .toArray(Query[]::new);

        dsl.batch(queries).execute();
    }

    @Override
    public List<String> getSubmissionLabels(Long submissionId) {

        return dsl.select(LABELS.NAME).from(SUBMISSION_LABELS).join(LABELS).on(LABELS.ID.eq(SUBMISSION_LABELS.LABEL_ID))
        .where(SUBMISSION_LABELS.SUBMISSION_ID.eq(submissionId)).fetchInto(String.class);

    }
    
}
