package com.example.CodeReviewApp.Repo.Implementations;

import com.example.CodeReviewApp.dto.User.UserDto;
import com.example.CodeReviewApp.dto.File.FileDto;

import static com.example.jooq.Tables.CODE;
import static com.example.jooq.Tables.ISSUES;
import static com.example.jooq.Tables.REVIEW_ASSIGNEES;
import static com.example.jooq.Tables.REVIEW_RESULTS;
import static com.example.jooq.Tables.SUBMISSION;
import static com.example.jooq.Tables.SUBMISSION_EXECUTION;
import static com.example.jooq.Tables.USERS;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.Submission;
import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.Repo.SubmissionRepository;
import com.example.CodeReviewApp.dto.Submissions.Out.HumanReviewResponse;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.QualityTestResultDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SonarIssue;
import com.example.CodeReviewApp.dto.Submissions.Out.SonarReviewResponse;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private final DSLContext dsl;

    public Long insertSubmission(Submission submission) {

        return dsl.insertInto(SUBMISSION)
                .columns(SUBMISSION.TITLE, SUBMISSION.DESCRIPTION, SUBMISSION.PROJECT_ID, SUBMISSION.SUBMITTER,SUBMISSION.STATUS)
                .values(submission.getTitle(), submission.getDescription(), submission.getProject_id(),
                        submission.getSubmitter(),SubmissionStatus.PENDING.name())
                .returning(SUBMISSION.ID)
                .fetchOne()
                .getId();
        }

    public List<ProjectSubmissionsDto> getProjectSubmissions(Long projectId) {

        return dsl.select(
                SUBMISSION.ID,
                SUBMISSION.TITLE,
                USERS.USERNAME.as("author"),
                SUBMISSION.STATUS)
                .from(SUBMISSION)
                .join(USERS).on(SUBMISSION.SUBMITTER.eq(USERS.ID))
                .where(SUBMISSION.PROJECT_ID.eq(projectId))
                .orderBy(SUBMISSION.ID.desc())
                .fetchInto(ProjectSubmissionsDto.class);

    }

    @Override
    public SubmissionDetailsDto getSubmissionDetails(Long submissionId) {

        var submissionRecord = dsl.select(
                SUBMISSION.ID,
                SUBMISSION.TITLE,
                SUBMISSION.DESCRIPTION,
                SUBMISSION.STATUS,
                USERS.ID.as("author_id"),
                USERS.USERNAME.as("author_username"))
                .from(SUBMISSION)
                .join(USERS)
                .on(SUBMISSION.SUBMITTER.eq(USERS.ID))
                .where(SUBMISSION.ID.eq(submissionId))
                .fetchOne();

        UserDto author = new UserDto(submissionRecord.get("author_id", Long.class),submissionRecord.get("author_username", String.class));
    
        if(submissionRecord.get("review_type").equals("SONARQUBE")){

                List<SonarIssue> sonarIssues = dsl.select(ISSUES.ID,ISSUES.SEVERITY,ISSUES.FILE_NAME,ISSUES.LINE_NUMBER,ISSUES.MESSAGE).from(ISSUES)                
                .where(ISSUES.SUBMISSION_ID.eq(submissionId))
                .fetch(record -> new SonarIssue(
                record.get(ISSUES.ID),
                record.get(ISSUES.SEVERITY),
                record.get(ISSUES.FILE_NAME),
                record.get(ISSUES.LINE_NUMBER),
                record.get(ISSUES.MESSAGE)));

                QualityTestResultDto qualityResult = dsl.select(REVIEW_RESULTS.CODE_SMELLS,REVIEW_RESULTS.BUGS,REVIEW_RESULTS.VULNERABILITIES,REVIEW_RESULTS.COVERAGE)
                .from(REVIEW_RESULTS).join(SUBMISSION_EXECUTION).on(REVIEW_RESULTS.SUBMISSION_EXECUTION_ID.eq(SUBMISSION_EXECUTION.ID))
                .where(SUBMISSION_EXECUTION.SUBMISSION_ID.eq(submissionId)).fetchOneInto(QualityTestResultDto.class);

                SonarReviewResponse sonarReviewResponse = new SonarReviewResponse(sonarIssues,qualityResult);
                
                return SubmissionDetailsDto.builder().sonarReviewResponse(sonarReviewResponse).reviewType("SONARQUBE").id(submissionId).build();

        }else{
                //files
                List<FileDto> files = dsl.select(
                CODE.ID,
                CODE.FILENAME,
                CODE.EXTENSION,
                CODE.SIZE)
                .from(CODE)
                .where(CODE.SUBMISSION_ID.eq(submissionId))
                .fetch(record -> new FileDto(
                        record.get(CODE.ID),
                        record.get(CODE.FILENAME),
                        record.get(CODE.EXTENSION),
                        record.get(CODE.SIZE)
                ));
    
                List<UserDto> reviewers = dsl.select(USERS.ID,USERS.USERNAME).from(REVIEW_ASSIGNEES).join(USERS).on(REVIEW_ASSIGNEES.REVIEWER_ID.eq(USERS.ID)).where(REVIEW_ASSIGNEES.REVIEW_ID.eq(submissionId))
                .fetch(record -> new UserDto(record.get(USERS.ID),record.get(USERS.USERNAME)));

                

                HumanReviewResponse humanReviewResponse = new HumanReviewResponse(submissionRecord.get(SUBMISSION.TITLE),
                        submissionRecord.get(SUBMISSION.DESCRIPTION),
                        author,
                        files,
                        reviewers,
                        submissionRecord.get(SUBMISSION.STATUS),
                        List.of()
                );
                
                return SubmissionDetailsDto.builder().humanReviewResponse(humanReviewResponse).reviewType("HUMAN").id(submissionId).build();
        } 
        }



    @Override
    public Submission getSubmission(Long submissionId) {
        
        return dsl.selectFrom(SUBMISSION).where(SUBMISSION.ID.eq(submissionId)).fetchOneInto(Submission.class);

    }

    @Override
    public void updateSubmissionStatus(Long submissionId, SubmissionStatus target) {

        dsl.update(SUBMISSION).set(SUBMISSION.STATUS, target.name()).where(SUBMISSION.ID.eq(submissionId)).execute();

        }

    @Override
    public boolean isSubmissionClosed(Long submissionId) {

        String statusValue = dsl.select(SUBMISSION.STATUS).from(SUBMISSION).where(SUBMISSION.ID.eq(submissionId)).fetchOne(SUBMISSION.STATUS);
    
        SubmissionStatus status = SubmissionStatus.valueOf(statusValue);

        if(status.equals(SubmissionStatus.PENDING)) return false;

        return true;

        }


}
