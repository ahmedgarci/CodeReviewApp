package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.CODE;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.Repo.CodeFileRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl  implements CodeFileRepository{

    private final DSLContext dsl;

    @Override
    public void insert(CodeFile code) {

        dsl.insertInto(CODE)
        .columns(CODE.SUBMISSION_ID,CODE.FILENAME,CODE.FILE_PATH,CODE.SIZE)
        .values(code.getSubmission_id(),code.getFilename(),code.getFile_path(),code.getSize())
        .execute();

    }

    @Override
    public List<CodeFile> findBySubmissionId(Long submissionId) {

        return dsl.select(CODE.FILENAME,CODE.SIZE,CODE.FILE_PATH).from(CODE)
            .where(CODE.SUBMISSION_ID.eq(submissionId))
            .fetchInto(CodeFile.class);

    }

    @Override
    public CodeFile getFileByIdAndSubmissionId(Long submissionId,Long fileId) {

        return
            dsl.selectFrom(CODE)
               .where(CODE.ID.eq(fileId))
               .and(CODE.SUBMISSION_ID.eq(submissionId))
               .fetchOneInto(CodeFile.class);
    
    }
    
}
