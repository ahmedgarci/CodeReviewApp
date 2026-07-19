package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.COMMENTS;
import static com.example.jooq.Tables.USERS;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.Comment;
import com.example.CodeReviewApp.Repo.CommentRespoitory;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements  CommentRespoitory{

    private final DSLContext dsl;

    @Override
    public void insert(Comment comment) {

        dsl.insertInto(COMMENTS).columns(COMMENTS.SUBMISSION_ID,COMMENTS.AUTHOR_ID,COMMENTS.CODE_ID,COMMENTS.CONTENT,COMMENTS.LINE_NUMBER)
            .values(comment.getSubmission_id(),comment.getAuthor_id(),comment.getCode_id(),comment.getContent(),comment.getLine_number()).execute();

    }

    @Override
public List<CommentResponseDto> getCommentsBySubmissionIdAndFileId(Long submissionId,Long fileId) {
    return dsl.select(
            COMMENTS.ID,
            USERS.USERNAME.as("author"),
            COMMENTS.CONTENT,
            COMMENTS.LINE_NUMBER.as("line_number"),
            COMMENTS.CREATED_AT.as("created_at")
        )
        .from(COMMENTS)
        .innerJoin(USERS)
        .on(COMMENTS.AUTHOR_ID.eq(USERS.ID))
        .where(
            COMMENTS.SUBMISSION_ID.eq(submissionId)
            .and(COMMENTS.CODE_ID.eq(fileId))
        )
        .orderBy(COMMENTS.LINE_NUMBER.asc())
        .fetchInto(CommentResponseDto.class);
}

}
