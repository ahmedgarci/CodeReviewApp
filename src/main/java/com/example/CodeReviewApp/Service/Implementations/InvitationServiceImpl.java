package com.example.CodeReviewApp.Service.Implementations;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Models.Invitation;
import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Models.Enums.InvitationStatus;
import com.example.CodeReviewApp.Repo.InvitationRepository;
import com.example.CodeReviewApp.Repo.ProjectMembersRepository;
import com.example.CodeReviewApp.Repo.UserRepository;
import com.example.CodeReviewApp.Service.InvitationService;
import com.example.CodeReviewApp.dto.Collaborators.In.InvitationDto;
import com.example.CodeReviewApp.exceptions.ActionNotAllowedException;
import com.example.CodeReviewApp.exceptions.RessourceNotFoundException;
import com.example.CodeReviewApp.mapper.InvitationFactory;
import com.example.CodeReviewApp.util.Auth.AuthenticationContext;
import com.example.CodeReviewApp.util.Mail.MailingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationFactory invitationFactory;
    private final AuthenticationContext authenticationContext;
    private final InvitationRepository invitationRepository;
    private final ProjectMembersRepository projectMembersRepository;
    private final MailingService mailingService;
    private final UserRepository userRepository;

    @Override
    public void sendInvitation(InvitationDto dto,Long project_id) {
        
        User receiver = userRepository.findByEmail(dto.email()).orElseThrow(()-> new RessourceNotFoundException("receiver_email"));

        if(projectMembersRepository.isUserAlreadyMemberOfProject(project_id, receiver.getId())) return;

        String token = generateInvitationToken();

        Invitation invitation = invitationFactory.create(authenticationContext.getCurrentUser().getId(),receiver.getId(), token, project_id);

        invitationRepository.insertInvitation(invitation);

        mailingService.sendEmail(dto.email(), "PROJECT INVITATION" , token);        
    
    }

    @Override
    public void acceptInvitation(String token) {

        Invitation invitation = invitationRepository.findInvitation(token).orElseThrow(()->new RessourceNotFoundException("invitation was not found"));

        if(!invitation.getInvitation_status().name().equals(InvitationStatus.PENDING.name())) throw new ActionNotAllowedException("invitation already proessed");

        if(invitation.getExpiresAt().isBefore(LocalDateTime.now())){

            invitationRepository.updateInvitationStatus(InvitationStatus.EXPIRED,invitation.getId());

            return;

        }

        invitationRepository.updateInvitationStatus(InvitationStatus.ACCEPTED, invitation.getId());

        projectMembersRepository.insertCollaboratorForProject(invitation.getProject_id(), invitation.getReceiver_id());

        
    }
    
    private String generateInvitationToken(){

        StringBuilder sb = new StringBuilder();
    
        for (int i = 0; i < 8; i++) {
    
            int num = (int) (Math.random() * 10);
    
            sb.append(num);
    
        }

        return sb.toString();

    }
}
