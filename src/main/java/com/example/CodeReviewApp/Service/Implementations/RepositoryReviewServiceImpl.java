package com.example.CodeReviewApp.Service.Implementations;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.SubmissionExecution;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.ProjectRepository;
import com.example.CodeReviewApp.Repo.SubmissionExecutionRepository;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.Service.RepoReviewService;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.mapper.SubmissionExecutionFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;
import com.example.CodeReviewApp.util.Producer.EventProducer;
import com.example.CodeReviewApp.util.Producer.out.Event;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepositoryReviewServiceImpl  implements RepoReviewService{

    private final ProjectRepository projectRepository;
    private final ProjectMembersRepository projectMembersRepository;
    private final AuthenticationContext authenticationContext;
    private final EventProducer eventProducer;
    private final SubmissionRepository submissionRepository;
    private final SubmissionExecutionFactory submissionExecutionFactory;
    private final SubmissionExecutionRepository submissionExecutionRepository;

    @Transactional
    public void analyze(Long projectId){

        Project project = projectRepository.getProjectById(projectId);

        User user = authenticationContext.getCurrentUser();

        boolean isMember = projectMembersRepository.isUserAlreadyMemberOfProject(project.getId(), user.getId());

        if(!isMember) throw new ActionNotAllowedException("user is not a member of this project");

        Submission submission = Submission.builder().title("Project Aalyzed By SonarQube").project_id(project.getId()).submitter(user.getId()).status(SubmissionStatus.PENDING).build();

        Long submissionId = submissionRepository.insertSubmission(submission);

        SubmissionExecution execution = submissionExecutionFactory.create(submissionId);

        submissionExecutionRepository.insert(execution);

        eventProducer.execute(new Event(project.getUrl(), project.getId(),submissionId,execution.getId()));

    }
    
}
