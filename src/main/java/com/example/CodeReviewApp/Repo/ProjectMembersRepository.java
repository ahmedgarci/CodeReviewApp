package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.dto.User.UserDto;

public interface ProjectMembersRepository {
    
    boolean isUserAlreadyMemberOfProject(Long project_id,Long user_id);

    void insertCollaboratorForProject(Long project_id,Long user_id);

    List<UserDto> getProjectCollaborators(Long projectId);    

    boolean reviewersAreProjectMembers(Long projectId,List<Long> reviewerIds);

    
}
