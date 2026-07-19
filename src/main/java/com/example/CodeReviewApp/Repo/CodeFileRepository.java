package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.Models.CodeFile;

public interface CodeFileRepository {

    void insert(CodeFile code);

    List<CodeFile> findBySubmissionId(Long submissionId);

    CodeFile getFileByIdAndSubmissionId(Long submissionId,Long fileId);
}
