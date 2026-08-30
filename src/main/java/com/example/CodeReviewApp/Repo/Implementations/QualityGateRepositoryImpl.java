package com.example.CodeReviewApp.Repo.Implementations;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.ProjectQualitySettings;
import com.example.CodeReviewApp.Repo.ProjectQualityGateSettingsRepository;
import static com.example.jooq.tables.ProjectSettings.PROJECT_SETTINGS;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QualityGateRepositoryImpl implements ProjectQualityGateSettingsRepository {

    private final DSLContext dslContext;

    @Override
    public void insert(ProjectQualitySettings projectSettings) {

        dslContext.insertInto(PROJECT_SETTINGS).columns(PROJECT_SETTINGS.MAX_BUGS,PROJECT_SETTINGS.MAX_CODE_SMELLS,PROJECT_SETTINGS.MAX_VULNERABILITIES,PROJECT_SETTINGS.MIN_COVERAGE,PROJECT_SETTINGS.PROJECT_ID)
        .values(projectSettings.getMax_bugs(),projectSettings.getMax_code_smells(),projectSettings.getMax_vulnerabilities(),projectSettings.getMin_coverage(),projectSettings.getProject_id())
        .execute();

    }

    @Override
    public ProjectQualitySettings getSettingsByProjectId(Long projectId) {

        return dslContext.selectFrom(PROJECT_SETTINGS).where(PROJECT_SETTINGS.PROJECT_ID.eq(projectId)).fetchOneInto(ProjectQualitySettings.class);

    }

    @Override
    public void disable(Long projectId) {

        dslContext.update(PROJECT_SETTINGS).set(PROJECT_SETTINGS.ENABLED,false).where(PROJECT_SETTINGS.PROJECT_ID.eq(projectId)).execute();

    }

    @Override
    public void enable(Long projectId) {

        dslContext.update(PROJECT_SETTINGS).set(PROJECT_SETTINGS.ENABLED,true).where(PROJECT_SETTINGS.PROJECT_ID.eq(projectId)).execute();
    }

    @Override
    public void modify(Long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }
    
}
