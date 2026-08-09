package com.example.TicketingService.Domain.UseCase.Ticket;


import com.example.TicketingService.Domain.Entities.Ticket;
import com.example.TicketingService.Domain.Repo.TicketRepo;


public class GetTicketUseCase {

    private TicketRepo ticketRepo;

    public GetTicketUseCase(TicketRepo implementationRepo){
        this.ticketRepo = implementationRepo;
    }

    public Ticket execute(String ticketId){
        return ticketRepo.findTicketById(ticketId);
    }

}