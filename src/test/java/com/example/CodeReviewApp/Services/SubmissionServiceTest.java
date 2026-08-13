package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.CodeFileRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Service.Implementations.SubmissionCreationServiceImpl;
import com.example.CodeReviewApp.dto.Submissions.In.PublishReviewDto;
import com.example.CodeReviewApp.mapper.SubmissionFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;
import com.example.CodeReviewApp.util.Files.FileService;
import com.example.CodeReviewApp.util.Files.FileValidationFacade;

@ExtendWith(MockitoExtension.class)
public class SubmissionServiceTest {
    
    @Mock
    private FileValidationFacade fileValidationFacade;
    @Mock
    private FileService fileService;
    @Mock
    private SubmissionRepository submissionRepository;
    @Mock
    private CodeFileRepository codefFileRepository;
    @Mock
    private AuthenticationContext authenticationContext;
    @Mock
    private SubmissionFactory submissionFactory;

    @InjectMocks
    private SubmissionCreationServiceImpl submissionService;


    @Test
    public void shouldPublishCode(){

        PublishReviewDto dto = new PublishReviewDto();

        Long projectId = 1L;

        Long submissionId = 2L;

        Submission submission = new Submission();

        submission.setId(submissionId);

        User user = new User();

        user.setId(3L);

        List<MultipartFile>  submittedFiles = new ArrayList<>();

        List<CodeFile> uploadedFiles = new ArrayList<>();

        uploadedFiles.add(new CodeFile());

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(submissionFactory.toSubmission(dto, projectId)).thenReturn(submission);

        when(submissionRepository.insertSubmission(submission)).thenReturn(submissionId);
        
        when(fileService.uploadFiles(submission.getId(),submittedFiles)).thenReturn(uploadedFiles);

        submissionService.publishCode(dto, submittedFiles, projectId);

        verify(fileValidationFacade).validate(submittedFiles);

        verify(submissionFactory).toSubmission(dto, projectId);

        verify(authenticationContext).getCurrentUser();

        assertEquals(user.getId(),submission.getSubmitter());

        verify(submissionRepository).insertSubmission(submission);

        verify(fileService).moveFilesToPermanentStorage(submission.getId());
        
        verify(fileService).uploadFiles(submissionId, submittedFiles);

    }

    @Test
    public void shouldDeleteFilesWhenUploadFailes(){
        PublishReviewDto dto = new PublishReviewDto();

        Long projectId = 1L;

        Long submissionId = 2L;

        Submission submission = new Submission();

        submission.setId(submissionId);

        User user = new User();

        user.setId(3L);

        List<MultipartFile>  submittedFiles = new ArrayList<>();

        List<CodeFile> uploadedFiles = new ArrayList<>();

        uploadedFiles.add(new CodeFile());

        when(submissionFactory.toSubmission(dto, projectId)).thenReturn(submission);

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(submissionRepository.insertSubmission(submission)).thenReturn(submissionId);

        when(fileService.uploadFiles(submissionId, submittedFiles)).thenThrow(new RuntimeException("upload files failed"));

        assertThrows(RuntimeException.class, ()-> submissionService.publishCode(dto, submittedFiles, projectId));
        
        verify(fileValidationFacade).validate(submittedFiles);

        verify(submissionFactory).toSubmission(dto, projectId);

        verify(authenticationContext).getCurrentUser();

        verify(fileService).deleteSubmissionFiles(submissionId);

        verifyNoInteractions(codefFileRepository);

        verify(fileService, never()).moveFilesToPermanentStorage(submissionId);

    }

    @Test
    public void shouldInsertAllFiles(){
        
        PublishReviewDto dto = new PublishReviewDto();
        
        Long projectId = 1L;
        
        Long submissionId = 2L;
    
        User user = new User();
        
        user.setId(3L);
    
        Submission submission = new Submission();
    
        CodeFile file1 = new CodeFile();
        
        CodeFile file2 = new CodeFile();
    
        List<MultipartFile> files = new ArrayList<>();
    
        List<CodeFile> uploadedFiles = List.of(file1, file2);

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(submissionFactory.toSubmission(dto, projectId)).thenReturn(submission);

        when(submissionRepository.insertSubmission(submission)).thenReturn(submissionId);

        when(fileService.uploadFiles(submissionId, files)).thenReturn(uploadedFiles);
        
        submissionService.publishCode(dto, files, projectId);

        verify(codefFileRepository).insert(file1);
    
        verify(codefFileRepository).insert(file2);

    }

    @Test
    public void shouldThrowWhenFilesValidationFails(){

        PublishReviewDto dto = new PublishReviewDto();
        
        Long projectId = 1L;
                
        List<MultipartFile> uploadedFiles = List.of();

        doThrow(new RuntimeException("files are not valid")).when(fileValidationFacade).validate(uploadedFiles);
        
        assertThrows(RuntimeException.class, ()->submissionService.publishCode(dto, uploadedFiles, projectId));

        verifyNoInteractions(submissionFactory);

        verifyNoInteractions(submissionRepository);

        verifyNoInteractions(fileService);

        verifyNoInteractions(codefFileRepository);

    }




  

}
