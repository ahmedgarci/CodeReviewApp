CREATE TABLE comments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    author_id BIGINT NOT NULL,
    code_id BIGINT NOT NULL,
    submission_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    line_number BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comments_author
        FOREIGN KEY (author_id) REFERENCES users(id),
    CONSTRAINT fk_comments_code
        FOREIGN KEY (code_id) REFERENCES code(id),
    CONSTRAINT fk_comments_submission
        FOREIGN KEY (submission_id) REFERENCES submission(id)
);