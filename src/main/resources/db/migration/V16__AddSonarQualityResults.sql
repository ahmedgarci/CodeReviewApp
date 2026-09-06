CREATE TABLE review_results (
    id BIGSERIAL PRIMARY KEY,
    submission_execution_id BIGINT NOT NULL,
    quality_gate_status VARCHAR(50) NOT NULL,
    bugs INT NOT NULL DEFAULT 0,
    vulnerabilities INT NOT NULL DEFAULT 0,
    code_smells INT NOT NULL DEFAULT 0,
    analysis_duration INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_results_execution
        FOREIGN KEY (submission_execution_id)
        REFERENCES submission_execution(id)
);