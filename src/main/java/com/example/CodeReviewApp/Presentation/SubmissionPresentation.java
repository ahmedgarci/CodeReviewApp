package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Service.RepoReviewService;
import com.example.CodeReviewApp.Service.SubmissionCreationService;
import com.example.CodeReviewApp.Service.SubmissionDetailsService;
import com.example.CodeReviewApp.dto.Submissions.In.PublishReviewDto;
import com.example.CodeReviewApp.dto.Submissions.In.SubmissionStatusDto;
import com.example.CodeReviewApp.dto.Submissions.Out.FileContentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionPresentation {
    

    private final SubmissionCreationService submissionCreationService;
    private final SubmissionDetailsService submissionDetailsService;
    private final RepoReviewService repoReviewService;

    @PostMapping("/{projectId}")
    public ResponseEntity<Void> createSubmissionRequest(@PathVariable Long projectId,

        @RequestPart("data") PublishReviewDto dto,

        @RequestPart("files") List<MultipartFile> files) {

            submissionCreationService.publishCode(dto,files, projectId);

        return ResponseEntity.status(HttpStatus.CREATED).build();        
    }

    @PostMapping("/repo/{projectId}")
    public ResponseEntity<Void> analyzeFullRepo(@PathVariable Long projectId) {

        repoReviewService.analyze(projectId);

        return ResponseEntity.ok().build();
    }
    

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectSubmissionsDto>> getProjectSubmission(@PathVariable Long projectId) {

        return ResponseEntity.ok().body(submissionDetailsService.getProjectSubmissions(projectId));

    }

    @GetMapping("{submissionId}/details")
    public ResponseEntity<SubmissionDetailsDto> getSubmissionDetails(@PathVariable Long submissionId) {

        return ResponseEntity.ok().body(submissionDetailsService.getSubmissionDetails(submissionId));

    }

    @GetMapping("/{submissionId}/file/{fileId}")
    public ResponseEntity<FileContentResponseDto> getFileContent(@PathVariable Long submissionId,@PathVariable Long fileId) {

        return ResponseEntity.ok().body(submissionDetailsService.getFileContent(submissionId, fileId));

    }

    @PostMapping("/status/{submissionId}")
    public ResponseEntity<Void> updateSubmissionStatus(@RequestBody SubmissionStatusDto submissionDto,@PathVariable Long submissionId) {
        
        submissionDetailsService.updateSubmissionStatus(submissionId, submissionDto.status());

        return ResponseEntity.ok().build();
    }
    


    
    
}
