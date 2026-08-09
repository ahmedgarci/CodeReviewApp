CREATE TABLE labels(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE submission_labels(
    id BIGSERIAL PRIMARY KEY,
    submission_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,

    CONSTRAINT submission_labels_submission_id_fk 
        FOREIGN KEY (submission_id) REFERENCES submission(id),

    CONSTRAINT submission_labels_label_id_fk 
        FOREIGN KEY (label_id) REFERENCES labels(id)
);