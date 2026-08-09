CREATE TABLE issues(
    id BIGSERIAL PRIMARY KEY,

    submission_id BIGINT NOT NULL,

    severity VARCHAR(30) NOT NULL,

    file_name VARCHAR(255) NOT NULL,

    line_number BIGINT NOT NULL,

    message VARCHAR(255),

    CONSTRAINT fk_issues_submission
        FOREIGN KEY (submission_id)
        REFERENCES submission(id)
);