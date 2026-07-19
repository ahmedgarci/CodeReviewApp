package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.Models.Comment;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;

public interface CommentRespoitory {

    void insert(Comment comment);
    
    List<CommentResponseDto> getCommentsBySubmissionIdAndFileId(Long submissionId,Long fileId);
} 
