CREATE TABLE project_settings (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT,
    max_bugs BIGINT,
    max_vulnerabilities BIGINT,
    max_code_smells BIGINT,
    min_coverage BIGINT,
    enabled BOOLEAN,
    CONSTRAINT fk_quality_gate_pid
        FOREIGN KEY (project_id)
        REFERENCES project(id)
);
