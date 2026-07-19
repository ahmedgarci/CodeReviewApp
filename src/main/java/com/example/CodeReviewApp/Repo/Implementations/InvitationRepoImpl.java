package com.example.CodeReviewApp.Repo.Implementations;

import static com.example.jooq.Tables.INVITATION;

import java.util.Optional;

import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.Invitation;
import com.example.CodeReviewApp.Models.Enums.InvitationStatus;
import com.example.CodeReviewApp.Repo.InvitationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InvitationRepoImpl implements InvitationRepository{

    private final DSLContext dsl;

    @Override
    public void insertInvitation(Invitation invitation) {
        
        dsl
        .insertInto(INVITATION).columns(INVITATION.PROJECT_ID,INVITATION.SENDER_ID,INVITATION.RECEIVER_ID,INVITATION.INVITATION_STATUS,INVITATION.TOKEN,INVITATION.EXPIRES_AT)
        .values(invitation.getProject_id(),invitation.getSender_id(),invitation.getReceiver_id(),invitation.getInvitation_status().name(),invitation.getToken(),invitation.getExpiresAt())
        .execute();

    }

    @Override
    public Optional<Invitation> findInvitation(String token) {

        return dsl.selectFrom(INVITATION).where(INVITATION.TOKEN.eq(token)).fetchOptionalInto(Invitation.class);

    }


    @Override
    public void updateInvitationStatus(InvitationStatus targetStatus,Long invitationId) {

        dsl.update(INVITATION).set(INVITATION.INVITATION_STATUS,targetStatus.name()).where(INVITATION.ID.eq(invitationId)).execute();

    }
    
}
