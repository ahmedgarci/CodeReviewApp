ALTER TABLE review_results
    DROP COLUMN quality_gate_status,
    DROP COLUMN analysis_duration,
    DROP COLUMN created_at,
    ADD COLUMN coverage INT NOT NULL DEFAULT 0;