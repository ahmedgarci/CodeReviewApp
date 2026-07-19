package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.PROJECT_MEMBERS;
import static com.example.jooq.Tables.USERS;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.dto.User.UserDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProjectMembersRepositoryImpl implements ProjectMembersRepository {

    private final DSLContext dsl;

    @Override
    public boolean isUserAlreadyMemberOfProject(Long project_id, Long user_id) {

        return dsl.fetchExists(PROJECT_MEMBERS,PROJECT_MEMBERS.PROJECT_ID.eq(project_id).and(PROJECT_MEMBERS.USER_ID.eq(user_id)));
    
    }

    @Override
    public void insertCollaboratorForProject(Long project_id, Long user_id) {
        
        dsl.insertInto(PROJECT_MEMBERS).columns(PROJECT_MEMBERS.PROJECT_ID,PROJECT_MEMBERS.USER_ID).values(project_id,user_id).execute();

    }

    @Override
    public List<UserDto> getProjectCollaborators(Long projectId) {

        return dsl.select(USERS.USERNAME,USERS.ID)
        .from(PROJECT_MEMBERS)
        .join(USERS)
        .on(PROJECT_MEMBERS.USER_ID.eq(USERS.ID))
        .where(PROJECT_MEMBERS.PROJECT_ID.eq(projectId))
        .fetchInto(UserDto.class);
    }
    
    
    public boolean reviewersAreProjectMembers(Long projectId,List<Long> reviewerIds) {

        if (reviewerIds == null || reviewerIds.isEmpty()) {
            return false;
        }

        int count = dsl.selectCount().from(PROJECT_MEMBERS).where(PROJECT_MEMBERS.PROJECT_ID.eq(projectId)).and(PROJECT_MEMBERS.USER_ID.in(reviewerIds))
                .fetchOne(0, int.class);

        return count == reviewerIds.size();

    }
}
