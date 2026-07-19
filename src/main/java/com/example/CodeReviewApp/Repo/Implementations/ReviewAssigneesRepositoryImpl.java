package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.REVIEW_ASSIGNEES;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Repo.ReviewAssigneesRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewAssigneesRepositoryImpl  implements ReviewAssigneesRepository{

    private final DSLContext dsl;

    @Override
    public void insert(Long submissionId,List<Long> reviewerIds) {

        var queries = reviewerIds.stream().map((id) -> dsl.insertInto(REVIEW_ASSIGNEES)
        .columns(REVIEW_ASSIGNEES.REVIEW_ID,REVIEW_ASSIGNEES.REVIEWER_ID)
        .values(submissionId,id)  
        ).toList();

        dsl.batch(queries).execute();

    }

    @Override
    public boolean isUserAllowedToReviewSubmission(Long submissionId, Long reviewerId) {

        return dsl.fetchExists(REVIEW_ASSIGNEES,REVIEW_ASSIGNEES.REVIEWER_ID.eq(reviewerId).and(REVIEW_ASSIGNEES.REVIEW_ID.eq(submissionId)));

    }
    
}
