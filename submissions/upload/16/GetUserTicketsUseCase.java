package com.example.TicketingService.Domain.UseCase.Ticket;

import java.util.List;

import com.example.TicketingService.Domain.Auth.AuthProvider;
import com.example.TicketingService.Domain.Entities.Ticket;
import com.example.TicketingService.Domain.Repo.TicketRepo;

public class GetUserTicketsUseCase {
    
    private TicketRepo ticketRepo;
    private AuthProvider authProvider;

    public GetUserTicketsUseCase(TicketRepo repo,AuthProvider authProviderImpl){
        this.ticketRepo = repo;
        this.authProvider = authProviderImpl;
    }

    public List<Ticket> execute(){
        return ticketRepo.findUserTickets();
    }
}
