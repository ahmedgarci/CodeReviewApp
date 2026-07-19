package com.example.CodeReviewApp.Service;

import com.example.CodeReviewApp.dto.Collaborators.In.InvitationDto;

public interface InvitationService {
    
    void sendInvitation(InvitationDto dto,Long project_id);

    void acceptInvitation(String token);

    
    
}
