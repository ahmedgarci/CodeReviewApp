CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    url VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_project_owner
        FOREIGN KEY (owner_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE project_members (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_project_members_project
        FOREIGN KEY (project_id) REFERENCES project(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_project_members_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT unique_project_user
        UNIQUE (project_id, user_id)
);

CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    submitter BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    project_id BIGINT NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_review_submitter
        FOREIGN KEY (submitter) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_review_project
        FOREIGN KEY (project_id) REFERENCES project(id)
        ON DELETE CASCADE
);

CREATE TABLE review_assignees (
    id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,
    CONSTRAINT fk_review_assignees_review
        FOREIGN KEY (review_id) REFERENCES review(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_review_assignees_user
        FOREIGN KEY (reviewer_id) REFERENCES users(id)
        ON DELETE CASCADE
);