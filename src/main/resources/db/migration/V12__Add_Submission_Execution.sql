CREATE TABLE submission_execution(
    id BIGSERIAL PRIMARY KEY,

    submission_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,
    
    error_message TEXT,

    started_at TIMESTAMP,
    completed_at TIMESTAMP,

    CONSTRAINT fk_execution_submission
        FOREIGN KEY (submission_id)
        REFERENCES submission(id)
        ON DELETE CASCADE
);