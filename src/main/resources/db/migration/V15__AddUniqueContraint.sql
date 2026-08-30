ALTER TABLE project_settings
ALTER COLUMN project_id SET NOT NULL;

ALTER TABLE project_settings
add CONSTRAINT uq_project_settings_project_id
UNIQUE (project_id);
