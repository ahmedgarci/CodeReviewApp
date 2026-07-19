package com.example.CodeReviewApp.Service.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.Models.Comment;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.CodeFileRepository;
import com.example.CodeReviewApp.Repo.CommentRespoitory;
import com.example.CodeReviewApp.Repo.ReviewAssigneesRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Service.CommentService;
import com.example.CodeReviewApp.dto.Comments.In.CreateCommentDto;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.mapper.CommentFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRespoitory commentRespoitory;
    private final CommentFactory commentFactory;
    private final ReviewAssigneesRepository reviewAssigneesRepository;
    private final CodeFileRepository codeFileRepository;
    private final AuthenticationContext authenticationContext;
    private final SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public void create(CreateCommentDto dto, Long submissionId, Long codeId) {

        User user = authenticationContext.getCurrentUser();

        boolean isClosed = submissionRepository.isSubmissionClosed(submissionId);

        if(isClosed) throw new ActionNotAllowedException("submission already approved or declined ");

        boolean isReviewer = reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId, user.getId());

        if(!isReviewer) throw new ActionNotAllowedException("user cannot review this submission");

        CodeFile codeFile = codeFileRepository.getFileByIdAndSubmissionId(submissionId,codeId);

        if(codeFile == null) throw new RessourceNotFoundException("file was not found for submission");

        Comment comment = commentFactory.toComment(dto,submissionId,codeFile.getId(),user.getId());

        commentRespoitory.insert(comment);        

    }

    @Override
    public List<CommentResponseDto> getFileComments(Long codeId,Long submissionId) {
                
        CodeFile codeFile = codeFileRepository.getFileByIdAndSubmissionId(submissionId, codeId);

        if(codeFile == null) throw new RessourceNotFoundException("file was not found for submission");

        return commentRespoitory.getCommentsBySubmissionIdAndFileId(submissionId, codeId);

    }

    



    
}
