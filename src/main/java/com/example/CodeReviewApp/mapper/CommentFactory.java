package com.example.CodeReviewApp.mapper;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.Comment;
import com.example.CodeReviewApp.dto.Comments.In.CreateCommentDto;

@Component
public class CommentFactory {

    public Comment toComment(CreateCommentDto dto, Long submissionId, Long codeId,Long author) {

        return Comment.builder()
                        .submission_id(submissionId)
                        .author_id(author)
                        .code_id(codeId)
                        .line_number(dto.line_number())
                        .content(dto.content())
                        .build();

    }
    
}
