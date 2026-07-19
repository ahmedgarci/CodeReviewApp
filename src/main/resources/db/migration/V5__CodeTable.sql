CREATE TABLE code (
    id BIGSERIAL PRIMARY KEY,
    submission_id BIGINT NOT NULL,
    filename VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    size BIGINT NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_code_submission
        FOREIGN KEY (submission_id)
        REFERENCES submission(id)
        ON DELETE CASCADE,

    CONSTRAINT unique_submission_filename
        UNIQUE (submission_id, filename)
);