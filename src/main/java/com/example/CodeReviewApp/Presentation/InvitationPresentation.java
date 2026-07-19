package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.Service.InvitationService;
import com.example.CodeReviewApp.dto.Collaborators.In.InvitationDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/invitation")
@RequiredArgsConstructor
public class InvitationPresentation {

    private final InvitationService invitationService;

    @PostMapping("/{projectId}")
    public ResponseEntity<Void> sendInvitation(@RequestBody InvitationDto invitationDto,@PathVariable Long projectId) {
        
        invitationService.sendInvitation(invitationDto, projectId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvitation(@RequestParam(name = "token",required = true) String token) {

        invitationService.acceptInvitation(token);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    



}
