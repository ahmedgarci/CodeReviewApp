package com.example.CodeReviewApp.Models;

import java.time.LocalDateTime;

import com.example.CodeReviewApp.Models.Enums.InvitationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {
    
    private Long id;

    private Long sender_id;

    private Long receiver_id;

    private Long project_id;

    private String token;

    private InvitationStatus invitation_status;

    private LocalDateTime expiresAt;
}
