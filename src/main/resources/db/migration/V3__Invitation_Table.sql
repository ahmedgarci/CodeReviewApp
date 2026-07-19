CREATE TABLE invitation (
    id BIGSERIAL PRIMARY KEY,

    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,

    token VARCHAR(30) NOT NULL UNIQUE,

    invitation_status VARCHAR(20) NOT NULL
        CHECK (invitation_status IN (
            'PENDING',
            'ACCEPTED',
            'REJECTED',
            'EXPIRED'
        )),

    expires_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_invitation_project_id
        FOREIGN KEY (project_id)
        REFERENCES project(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_invitation_sender_id
        FOREIGN KEY (sender_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_invitation_receiver_id
        FOREIGN KEY (receiver_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_invitation UNIQUE (receiver_id, project_id)
);