package com.example.CodeReviewApp.Service.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;

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
import com.example.CodeReviewApp.Service.SubmissionDetailsService;
import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.FileContentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;
import com.example.CodeReviewApp.util.Files.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionDetailsServiceImpl  implements SubmissionDetailsService{

    private final FileService fileService;
    private final SubmissionRepository submissionRepository;
    private final CodeFileRepository codefFileRepository;
    private final AuthenticationContext authenticationContext;
    private final ProjectMembersRepository projectMembersRepository;
    private final ReviewAssigneesRepository reviewAssigneesRepository;
    private final CommentRespoitory commentRespoitory;
    private final SubmissionLabelsRepository submissionLabelsRepository;



    public List<ProjectSubmissionsDto> getProjectSubmissions(Long projectId){

        User authenticatedUser = authenticationContext.getCurrentUser();

        if(!projectMembersRepository.isUserAlreadyMemberOfProject(projectId, authenticatedUser.getId())){

            throw new ActionNotAllowedException("You are not a member of this project" );

        }

        List<ProjectSubmissionsDto> submissions = submissionRepository.getProjectSubmissions(projectId);

        if (submissions == null || submissions.isEmpty()) return List.of();

        return submissions;

    }
    

    public SubmissionDetailsDto getSubmissionDetails(Long submissionId){

    SubmissionDetailsDto submission = submissionRepository.getSubmissionDetails(submissionId);

    if (submission == null) {
        
        throw new RessourceNotFoundException("Submission not found");
    
    }

    List<String> labels = submissionLabelsRepository.getSubmissionLabels(submissionId);

    submission.setLabels(labels);

    return submission;
    
    }


    @Override
    public FileContentResponseDto getFileContent(Long submissionId, Long fileId) {

        User user = authenticationContext.getCurrentUser();

        Submission submission = submissionRepository.getSubmission(submissionId);

        CodeFile codeFile = codefFileRepository.getFileByIdAndSubmissionId(submissionId, fileId);

        if(codeFile == null) throw new ActionNotAllowedException("file was not found on server");

        boolean isMember = projectMembersRepository.isUserAlreadyMemberOfProject(submission.getProject_id(),user.getId());

        if(!isMember) throw new ActionNotAllowedException("user is not allowed to access the ressouce");

        byte content[] = fileService.readFile(codeFile.getFile_path());

        List<CommentResponseDto> comments = commentRespoitory.getCommentsBySubmissionIdAndFileId(submissionId, fileId);

        return new FileContentResponseDto(content, comments);
    }



    @Override
    public void updateSubmissionStatus(Long submissionId, SubmissionStatus target) {

        Submission submission = submissionRepository.getSubmission(submissionId);

        if(submission == null) throw new RessourceNotFoundException("submission not found");

        SubmissionStatus current = submission.getStatus();

        if(! isValidTransition(current,target)) throw new RuntimeException("transition not Valid");
        
        User user = authenticationContext.getCurrentUser();

        if(submission.getSubmitter().equals(user.getId())) throw new ActionNotAllowedException("author is not allowed to review its submission");

        if(!reviewAssigneesRepository.isUserAllowedToReviewSubmission(submissionId, user.getId()))  throw new ActionNotAllowedException("user not authorized to review a submission");    

        submissionRepository.updateSubmissionStatus(submissionId,target);    
    
    }
        
        
        
    
    private boolean isValidTransition(SubmissionStatus current, SubmissionStatus target) {

        return switch (current) {
            case PENDING -> target == SubmissionStatus.DECLINED || target == SubmissionStatus.APPROVED;
            case APPROVED, DECLINED -> false;
        };

    }    
    
        




}
