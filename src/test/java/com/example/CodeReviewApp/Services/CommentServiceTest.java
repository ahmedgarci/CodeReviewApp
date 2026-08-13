package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.Models.Comment;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.CodeFileRepository;
import com.example.CodeReviewApp.Repo.CommentRespoitory;
import com.example.CodeReviewApp.Repo.ReviewAssigneesRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Service.Implementations.CommentServiceImpl;
import com.example.CodeReviewApp.dto.Comments.In.CreateCommentDto;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.mapper.CommentFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    

    @Mock
    private CommentRespoitory commentRespoitory;
    @Mock
    private CommentFactory commentFactory;
    @Mock
    private ReviewAssigneesRepository reviewAssigneesRepository;
    @Mock
    private CodeFileRepository codeFileRepository;
    @Mock
    private AuthenticationContext authenticationContext;
    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private CommentServiceImpl commentService;


    @Nested
    public class CommentMethodTests {
        
        @Test
        public void shouldCreateComment(){
    
            CreateCommentDto createCommentDto = new CreateCommentDto(1L,"content");
    
            Long codeId = 3L;
    
            Long submissionId = 1L;
    
            User user = new User();
    
            user.setId(2L);
    
            CodeFile codeFile = new CodeFile();
    
            codeFile.setId(codeId);
    
            Comment comment = new Comment();
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(submissionRepository.isSubmissionClosed(submissionId)).thenReturn(false);
    
            when(reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId, user.getId())).thenReturn(true);
    
            when(codeFileRepository.getFileByIdAndSubmissionId(submissionId, codeId)).thenReturn(codeFile);
    
            when(commentFactory.toComment(createCommentDto, submissionId, codeId, user.getId())).thenReturn(comment);
    
            commentService.create(createCommentDto, submissionId, codeId);
    
            verify(authenticationContext).getCurrentUser();
            
            verify(submissionRepository).isSubmissionClosed(submissionId);
    
            verify(reviewAssigneesRepository).isUserAllowedToReviewSubmission(submissionId,user.getId());
            
            verify(codeFileRepository).getFileByIdAndSubmissionId(submissionId,codeId);
            
            verify(commentFactory).toComment(createCommentDto, submissionId, codeId, user.getId());
    
    
        }
    
        @Test
        public void shouldThrowWhenSubmissionIsClosed(){
            CreateCommentDto createCommentDto = new CreateCommentDto(1L,"content");
    
            Long codeId = 3L;
    
            Long submissionId = 1L;
    
            User user = new User();
    
            user.setId(2L);
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(submissionRepository.isSubmissionClosed(submissionId)).thenReturn(true);
    
            assertThrows(ActionNotAllowedException.class,()-> commentService.create(createCommentDto, submissionId, codeId));
    
            verify(submissionRepository).isSubmissionClosed(submissionId);
    
            verifyNoInteractions(reviewAssigneesRepository);
    
            verifyNoInteractions(codeFileRepository);
    
            verifyNoInteractions(commentFactory);
    
            verifyNoInteractions(commentRespoitory);
    
    
        }
    
        @Test
        public void shouldThrowWhenUserIsNotReviewer(){
            CreateCommentDto createCommentDto = new CreateCommentDto(1L,"content");
    
            Long codeId = 3L;
    
            Long submissionId = 1L;
    
            User user = new User();
    
            user.setId(2L);
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(submissionRepository.isSubmissionClosed(submissionId)).thenReturn(false);
    
            when(reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId, user.getId())).thenReturn(false);
    
            assertThrows(ActionNotAllowedException.class,()->commentService.create(createCommentDto, submissionId, codeId));
    
            verify(authenticationContext).getCurrentUser();
            
            verify(submissionRepository).isSubmissionClosed(submissionId);
    
            verify(reviewAssigneesRepository).isUserAllowedToReviewSubmission(submissionId,user.getId());
            
            verifyNoInteractions(codeFileRepository);
    
            verifyNoInteractions(commentFactory);
    
            verifyNoInteractions(commentRespoitory);
    
    
        }
    
        @Test
        public void shouldThrowWhenFilesNotFound(){
    
            CreateCommentDto createCommentDto = new CreateCommentDto(1L,"content");
    
            Long codeId = 3L;
    
            Long submissionId = 1L;
    
            User user = new User();
    
            user.setId(2L);
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(submissionRepository.isSubmissionClosed(submissionId)).thenReturn(false);
    
            when(reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId, user.getId())).thenReturn(true);
    
            when(codeFileRepository.getFileByIdAndSubmissionId(submissionId, codeId)).thenReturn(null);
    
            assertThrows(RessourceNotFoundException.class, ()->commentService.create(createCommentDto, submissionId, codeId));
    
            verify(authenticationContext).getCurrentUser();
            
            verify(submissionRepository).isSubmissionClosed(submissionId);
    
            verify(reviewAssigneesRepository).isUserAllowedToReviewSubmission(submissionId,user.getId());
            
            verify(codeFileRepository).getFileByIdAndSubmissionId(submissionId,codeId);
    
            verifyNoInteractions(commentFactory);
    
            verifyNoInteractions(commentRespoitory);
    
        }
    
        
    }

    @Nested
    public class InnerCommentServiceTest {

        public void shouldReturnComments(){
            Long codeId = 1L , submissionId = 2L;

            CodeFile codeFile = new CodeFile();

            when(codeFileRepository.getFileByIdAndSubmissionId(submissionId, codeId)).thenReturn(codeFile);


            List<CommentResponseDto> expectedResult = List.of(new CommentResponseDto());

            when(commentRespoitory.getCommentsBySubmissionIdAndFileId(submissionId, codeId)).thenReturn(expectedResult);

            List<CommentResponseDto> result = commentService.getFileComments(codeId, submissionId);

            verify(codeFileRepository).getFileByIdAndSubmissionId(submissionId, codeId);

            verify(commentRespoitory).getCommentsBySubmissionIdAndFileId(submissionId, codeId);

            assertEquals(result.size(), expectedResult.size());
        }

        @Test
        public void shouldThrowWhenCodeNotFound(){
            Long codeId = 1L , submissionId = 2L;

            when(codeFileRepository.getFileByIdAndSubmissionId(submissionId, codeId)).thenReturn(null);

            assertThrows(RessourceNotFoundException.class, ()->commentService.getFileComments(codeId, submissionId)); 

            verify(codeFileRepository).getFileByIdAndSubmissionId(submissionId, codeId);

            verifyNoInteractions(commentRespoitory);

            
        }




        
    }


}
