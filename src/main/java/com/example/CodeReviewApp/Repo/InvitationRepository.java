package com.example.CodeReviewApp.Repo;

import java.util.Optional;

import com.example.CodeReviewApp.Models.Invitation;
import com.example.CodeReviewApp.Models.Enums.InvitationStatus;

public interface InvitationRepository {
    
    void insertInvitation(Invitation invitation);

    Optional<Invitation> findInvitation(String token);

    void updateInvitationStatus(InvitationStatus targetStatus,Long invitationId);
}
