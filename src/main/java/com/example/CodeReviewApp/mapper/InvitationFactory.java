package com.example.CodeReviewApp.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.CodeReviewApp.Models.Invitation;
import com.example.CodeReviewApp.Models.Enums.InvitationStatus;

@Component
public class InvitationFactory {
    
    private final static Integer EXPIRATION_MINUTES = 30;
    
    public Invitation create(Long sender,Long receiver,String token,Long project_id){
        
        return Invitation.builder()
                        .sender_id(sender)
                        .receiver_id(receiver)
                        .token(token)
                        .invitation_status(InvitationStatus.PENDING)
                        .project_id(project_id)
                        .expiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES))
                        .build();
                        

    }

}
