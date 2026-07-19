package com.example.CodeReviewApp.Service;

import java.util.List;

import com.example.CodeReviewApp.dto.Comments.In.CreateCommentDto;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;

public interface CommentService {
    
    public void create(CreateCommentDto dto,Long submissionId, Long codeId);

    List<CommentResponseDto> getFileComments(Long codeId,Long submissionId);

}
