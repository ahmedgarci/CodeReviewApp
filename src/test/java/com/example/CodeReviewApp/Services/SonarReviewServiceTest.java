package com.example.CodeReviewApp.Services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.Implementations.ProjectMembersRepositoryImpl;
import com.example.CodeReviewApp.Repo.Implementations.ProjectRepositoryImpl;
import com.example.CodeReviewApp.Repo.Implementations.SubmissionExecutionRepositoryImpl;
import com.example.CodeReviewApp.Repo.Implementations.SubmissionRepositoryImpl;
import com.example.CodeReviewApp.Service.Implementations.RepositoryReviewServiceImpl;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.mapper.SubmissionExecutionFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContextImpl;
import com.example.CodeReviewApp.util.Producer.EventProducerImpl;

@ExtendWith(MockitoExtension.class)
public class SonarReviewServiceTest {
    
    @Mock
    private ProjectRepositoryImpl projectRepository;
    
    @Mock
    private ProjectMembersRepositoryImpl projectMembersRepository;
    
    @Mock
    private AuthenticationContextImpl authenticationContext;
    
    @Mock
    private EventProducerImpl eventProducer;
    
    @Mock
    private SubmissionRepositoryImpl submissionRepository;
    
    @Mock
    private SubmissionExecutionFactory submissionExecutionFactory;
    
    @Mock
    private SubmissionExecutionRepositoryImpl submissionExecutionRepository;

    @InjectMocks
    private RepositoryReviewServiceImpl repoReviewService;

    @Test
    public void shoulAnalyzeSuccessfully(){

        Long projectId = 1L;

        Project project = new Project();

        project.setId(projectId);

        User user = new User();

        user.setId(2L);

        Long submissionId = 3L;

        SubmissionExecution execution =  new SubmissionExecution();

        Long executionId = 4L;

        when(projectRepository.getProjectById(projectId)).thenReturn(project);

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(projectMembersRepository.isUserAlreadyMemberOfProject(project.getId(), user.getId())).thenReturn(true);

        when(submissionRepository.insertSubmission(any(Submission.class))).thenReturn(submissionId);

        when(submissionExecutionFactory.create(submissionId)).thenReturn(execution);

        when(submissionExecutionRepository.insert(execution)).thenReturn(executionId);

        repoReviewService.analyze(projectId);

        verify(projectRepository).getProjectById(projectId);

        verify(authenticationContext).getCurrentUser();

        verify(projectMembersRepository).isUserAlreadyMemberOfProject(project.getId(), user.getId());

        verify(submissionRepository).insertSubmission(any(Submission.class));

        verify(submissionExecutionFactory).create(submissionId);

        verify(submissionExecutionRepository).insert(execution);

        verify(eventProducer).execute(any());
    }

    @Test
    public void shoulThrowWhenUserIsNotMember(){

        Long projectId = 1L;

        Project project = new Project();

        project.setId(projectId);

        User user = new User();

        user.setId(2L);

        when(projectRepository.getProjectById(projectId)).thenReturn(project);

        when(authenticationContext.getCurrentUser()).thenReturn(user);

        when(projectMembersRepository.isUserAlreadyMemberOfProject(project.getId(), user.getId())).thenReturn(false);

        assertThrows(ActionNotAllowedException.class,()-> repoReviewService.analyze(projectId));

        verify(projectRepository).getProjectById(projectId);

        verify(authenticationContext).getCurrentUser();

        verify(projectMembersRepository).isUserAlreadyMemberOfProject(project.getId(), user.getId());

        verifyNoInteractions(submissionRepository);

        verifyNoInteractions(submissionExecutionFactory);

        verifyNoInteractions(submissionExecutionRepository);

        verifyNoInteractions(eventProducer);
    }
    
}

