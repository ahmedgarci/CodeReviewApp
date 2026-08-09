package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.PROJECT;
import static com.example.jooq.Tables.PROJECT_MEMBERS;
import static com.example.jooq.Tables.USERS;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.Project;
import com.example.CodeReviewApp.Repo.ProjectRepository;
import com.example.CodeReviewApp.dto.Project.Out.ProjectDetails;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository{

    private final DSLContext dsl;

    @Override
    public void insertProject(Project project) {

        Long projectId=  dsl.insertInto(PROJECT).columns(PROJECT.NAME,PROJECT.URL,PROJECT.OWNER_ID).values(project.getName(),project.getUrl(),project.getOwner_id()).returning(PROJECT.ID).fetchOne().get(PROJECT.ID);       
        
        dsl.insertInto(PROJECT_MEMBERS).columns(PROJECT_MEMBERS.PROJECT_ID,PROJECT_MEMBERS.USER_ID).values(projectId,project.getOwner_id()).execute();

    }

    @Override
    public List<ProjectDetails> getUserInvolvedProjects(Long user_id) {

        return dsl.select(PROJECT.ID,PROJECT.NAME,PROJECT.URL,USERS.USERNAME.as("owner_name"))
            .from(PROJECT_MEMBERS)
            .join(PROJECT)
            .on(PROJECT.ID.eq(PROJECT_MEMBERS.PROJECT_ID))
            .join(USERS)
            .on(PROJECT.OWNER_ID.eq(USERS.ID))
            .where(PROJECT_MEMBERS.USER_ID.eq(user_id))
            .fetchInto(ProjectDetails.class);

    }

    @Override
    public Project getProjectById(Long projectId) {

        return dsl.select(PROJECT.fields()).from(PROJECT).where(PROJECT.ID.eq(projectId)).fetchOneInto(Project.class);


    }


    
}
