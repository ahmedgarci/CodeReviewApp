package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.Repo.CodeFileRepository;
import com.example.CodeReviewApp.Repo.CommentRespoitory;
import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.ReviewAssigneesRepository;
import com.example.CodeReviewApp.Repo.SubmissionLabelsRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Service.Implementations.SubmissionDetailsServiceImpl;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.FileContentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;
import com.example.CodeReviewApp.util.Files.FileService;

@ExtendWith(MockitoExtension.class)
public class SubmissionDetailsServiceTest {
    
    @Mock
    private  FileService fileService;
    @Mock
    private  SubmissionRepository submissionRepository;
    @Mock
    private  CodeFileRepository codefFileRepository;
    @Mock
    private  AuthenticationContext authenticationContext;
    @Mock
    private  ProjectMembersRepository projectMembersRepository;
    @Mock
    private  ReviewAssigneesRepository reviewAssigneesRepository;
    @Mock
    private CommentRespoitory commentRespoitory;
    @Mock
    private SubmissionLabelsRepository submissionLabelsRepository;

    @InjectMocks
    private SubmissionDetailsServiceImpl submissionDetailsServiceImpl;

    
    @Nested
    public class ProjectSubmissionsTests {
        @Test
        public void shouldReturnProjectSubmissions(){
    
            User user = new User();
    
            user.setId(10L);
    
            Long projectId = 1L;
    
            List<ProjectSubmissionsDto> projectSubmissions = new ArrayList<>();
    
            projectSubmissions.add(new ProjectSubmissionsDto());
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(projectMembersRepository.isUserAlreadyMemberOfProject(projectId, user.getId())).thenReturn(true);
    
            when(submissionRepository.getProjectSubmissions(projectId)).thenReturn(projectSubmissions);
    
            List<ProjectSubmissionsDto> result = submissionDetailsServiceImpl.getProjectSubmissions(projectId);
    
            assertEquals(projectSubmissions, result);
    
            verify(authenticationContext).getCurrentUser();
    
            verify(projectMembersRepository).isUserAlreadyMemberOfProject(projectId, user.getId());
    
            verify(submissionRepository).getProjectSubmissions(projectId);
    
        }
        
        @Test
        public void shouldThrowWhenUserIsNotProjectMember(){

        User user = new User();

        user.setId(10L);

        Long projectId = 1L;

        List<ProjectSubmissionsDto> projectSubmissions = new ArrayList<>();

        projectSubmissions.add(new ProjectSubmissionsDto());

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(projectMembersRepository.isUserAlreadyMemberOfProject(projectId, user.getId())).thenReturn(false);

        assertThrows(ActionNotAllowedException.class, ()->submissionDetailsServiceImpl.getProjectSubmissions(projectId));

        verify(authenticationContext).getCurrentUser();

        verify(projectMembersRepository).isUserAlreadyMemberOfProject(projectId, user.getId());

        verify(submissionRepository,never()).getProjectSubmissions(projectId);

        }
        
        @Test
        public void shouldReturnEmptyProjectDetails(){

        User user = new User();

        user.setId(10L);

        Long projectId = 1L;

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(projectMembersRepository.isUserAlreadyMemberOfProject(projectId, user.getId())).thenReturn(true);

        when(submissionRepository.getProjectSubmissions(projectId)).thenReturn(List.of());

        List<ProjectSubmissionsDto> result = submissionDetailsServiceImpl.getProjectSubmissions(projectId);

        verify(authenticationContext).getCurrentUser();

        verify(projectMembersRepository).isUserAlreadyMemberOfProject(projectId, user.getId());

        assertEquals(result.size(),0);

    }
        
    }

    
    @Nested
    class SubmissionDetailsTests{
        
        @Test
        public void shouldReturnSubmissionDetails(){

        Long submissionId = 10L;

        SubmissionDetailsDto submissionDetailsDto = new SubmissionDetailsDto();

        List<String> labels = new ArrayList<>();

        labels.add("PERFORMACE");

        when(submissionRepository.getSubmissionDetails(submissionId)).thenReturn(submissionDetailsDto);

        when(submissionLabelsRepository.getSubmissionLabels(submissionId)).thenReturn(labels);

        SubmissionDetailsDto result = submissionDetailsServiceImpl.getSubmissionDetails(submissionId);

        verify(submissionRepository).getSubmissionDetails(submissionId);

        verify(submissionLabelsRepository).getSubmissionLabels(submissionId);

        assertEquals(submissionDetailsDto, result);

        assertEquals(labels, result.getLabels());

    }
    @Test
    public void shouldThrowWhenSubmissionNotFound(){

        Long submissionId = 10L;

        when(submissionRepository.getSubmissionDetails(submissionId)).thenReturn(null);

        assertThrows(RessourceNotFoundException.class,()-> submissionDetailsServiceImpl.getSubmissionDetails(submissionId));

        verify(submissionRepository).getSubmissionDetails(submissionId);

        verify(submissionLabelsRepository,never()).getSubmissionLabels(anyLong());

        }    
    }



    @Nested
    class FileContentTests{ 
        
        @Test
        void shouldReturnFileContent() {
        
        Long submissionId = 1L;

        Long fileId = 2L;

        User user = new User();

        user.setId(10L);

        Submission submission = new Submission();

        submission.setProject_id(20L);

        CodeFile codeFile = new CodeFile();

        codeFile.setFile_path("/files/test.java");

        byte[] fileContent = "hello world".getBytes();

        List<CommentResponseDto> comments = new ArrayList<>();
        comments.add(new CommentResponseDto());

        // Current user
        when(authenticationContext.getCurrentUser()).thenReturn(user);

        // Submission
        when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);

        // File
        when(codefFileRepository.getFileByIdAndSubmissionId(submissionId, fileId)).thenReturn(codeFile);

        // User is project member
        when(projectMembersRepository.isUserAlreadyMemberOfProject(submission.getProject_id(), user.getId())).thenReturn(true);

        // File content
        when(fileService.readFile(codeFile.getFile_path())).thenReturn(fileContent);

        // Comments
        when(commentRespoitory.getCommentsBySubmissionIdAndFileId(submissionId, fileId)).thenReturn(comments);

        // Act
        FileContentResponseDto result = submissionDetailsServiceImpl.getFileContent(submissionId, fileId);

        // Assert
        assertNotNull(result);

        assertArrayEquals(fileContent, result.getContent());

        assertEquals(comments, result.getComments());

        // Verify
        verify(authenticationContext).getCurrentUser();
  
        verify(submissionRepository).getSubmission(submissionId);
  
        verify(codefFileRepository).getFileByIdAndSubmissionId(submissionId, fileId);

        verify(projectMembersRepository).isUserAlreadyMemberOfProject(submission.getProject_id(), user.getId());

        verify(fileService).readFile(codeFile.getFile_path());

        verify(commentRespoitory).getCommentsBySubmissionIdAndFileId(submissionId, fileId);

    }

    @Test
    public void shouldThrowWhenFileNotFound(){
        Long submissionId = 1L;

        Long fileId = 2L;
    
        User user = new User();
    
        user.setId(10L);
    
        Submission submission = new Submission();
        
        // Current user
        when(authenticationContext.getCurrentUser()).thenReturn(user);
    
        // Submission
        when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);
    
        // File
        when(codefFileRepository.getFileByIdAndSubmissionId(submissionId, fileId)).thenReturn(null);
    
        assertThrows(ActionNotAllowedException.class, ()-> submissionDetailsServiceImpl.getFileContent(submissionId, fileId));
        
        verify(authenticationContext).getCurrentUser();

        verify(submissionRepository).getSubmission(submissionId);
    
        verify(codefFileRepository).getFileByIdAndSubmissionId(submissionId, fileId);

        verifyNoInteractions(projectMembersRepository);

        verifyNoInteractions(fileService);

        verifyNoInteractions(commentRespoitory);

    }

    @Test
    public void shouldThrowWhenUserIsNotProjectMember(){

        Long submissionId = 1L;

        Long fileId = 2L;
    
        User user = new User();
    
        user.setId(10L);
    
        Submission submission = new Submission();

        submission.setProject_id(3L);

        CodeFile codeFile = new CodeFile();

        codeFile.setFile_path("/files/test.java");        
        // Current user
        when(authenticationContext.getCurrentUser()).thenReturn(user);
    
        // Submission
        when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);
    
        // File
        when(codefFileRepository.getFileByIdAndSubmissionId(submissionId, fileId)).thenReturn(codeFile);

        when(projectMembersRepository.isUserAlreadyMemberOfProject(submission.getProject_id(),user.getId())).thenReturn(false);
    
        assertThrows(ActionNotAllowedException.class, ()-> submissionDetailsServiceImpl.getFileContent(submissionId, fileId));
        
        verify(authenticationContext).getCurrentUser();

        verify(submissionRepository).getSubmission(submissionId);
    
        verify(codefFileRepository).getFileByIdAndSubmissionId(submissionId, fileId);

        verify(projectMembersRepository).isUserAlreadyMemberOfProject(submission.getProject_id(),user.getId());

        verifyNoInteractions(fileService);

        verifyNoInteractions(commentRespoitory);

    }


    }



    @Nested
    class SubmisionStatusTests{

        @Test
        public void shouldUpdateSubmissionStatus(){
        
            Long submissionId = 1L;
    
            Submission submission = new Submission();

            submission.setSubmitter(20L);
    
            submission.setStatus(SubmissionStatus.PENDING);
    
            User user = new User();
    
            user.setId(3L);
    
            SubmissionStatus target = SubmissionStatus.APPROVED;
    
            when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId,user.getId())).thenReturn(true);
    
            submissionDetailsServiceImpl.updateSubmissionStatus(submissionId, target);
    
            verify(submissionRepository).updateSubmissionStatus(submissionId, target);
    
        }

        @Test
        public void shouldThrowWhenSubmissionNotFound(){
    
            Long submissionId = 1L;
    
            when(submissionRepository.getSubmission(submissionId)).thenReturn(null);
    
            SubmissionStatus target = SubmissionStatus.APPROVED;
    
            assertThrows(RessourceNotFoundException.class, ()-> submissionDetailsServiceImpl.updateSubmissionStatus(submissionId,target));
    
            verifyNoInteractions(authenticationContext);
    
            verifyNoInteractions(reviewAssigneesRepository);
    
            verify(submissionRepository,never()).updateSubmissionStatus(submissionId, target);
    
        }
        
        @Test
        public void shouldThrowWhenUserIsNotAllowedToReview(){
    
            Long submissionId = 1L;
    
            Submission submission = new Submission();

            submission.setSubmitter(20L);
    
            submission.setStatus(SubmissionStatus.PENDING);
    
            User user = new User();
    
            user.setId(3L);
    
            SubmissionStatus target = SubmissionStatus.APPROVED;
    
            when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
    
            when(reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId,user.getId())).thenReturn(false);
            
            assertThrows(ActionNotAllowedException.class, ()-> submissionDetailsServiceImpl.updateSubmissionStatus(submissionId, target));
    
            verify(submissionRepository,never()).updateSubmissionStatus(submissionId, target);
    
        }   


        @Test
        public void shouldThrowWhenAuthorTriesToReview(){
    
            Long submissionId = 1L;
    
            Submission submission = new Submission();
    
            submission.setSubmitter(3L);
    
            submission.setStatus(SubmissionStatus.PENDING);
    
            User user = new User();
    
            user.setId(3L);
    
            SubmissionStatus target = SubmissionStatus.APPROVED;
    
            when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);
    
            when(authenticationContext.getCurrentUser()).thenReturn(user);
                
            assertThrows(ActionNotAllowedException.class, ()-> submissionDetailsServiceImpl.updateSubmissionStatus(submissionId, target));
    
            verify(reviewAssigneesRepository, never()).isUserAllowedToReviewSubmission(anyLong(), anyLong());
    
            verify(submissionRepository,never()).updateSubmissionStatus(submissionId, target);
    
        }
    
        @Test
        public void shouldThrowWhenTransitionIsInvalid(){
    
            Long submissionId = 1L;
    
            Submission submission = new Submission();
    
            submission.setSubmitter(3L);
    
            submission.setStatus(SubmissionStatus.APPROVED);
    
            User user = new User();
    
            user.setId(3L);
    
            SubmissionStatus target = SubmissionStatus.PENDING;
    
            when(submissionRepository.getSubmission(submissionId)).thenReturn(submission);
            
            assertThrows(RuntimeException.class, ()-> submissionDetailsServiceImpl.updateSubmissionStatus(submissionId, target));
    
            verifyNoInteractions(authenticationContext);
    
            verifyNoInteractions(reviewAssigneesRepository);
        
            verify(submissionRepository, never()).updateSubmissionStatus(anyLong(), any());
    
        }


    }


  










}
