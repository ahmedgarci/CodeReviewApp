package com.example.TicketingService.Domain.UseCase.Ticket;


import java.util.List;
import java.util.Map;

import com.example.TicketingService.Domain.Entities.Ticket;
import com.example.TicketingService.Domain.Entities.Enums.Role;
import com.example.TicketingService.Domain.Entities.Enums.Status;
import com.example.TicketingService.Domain.Repo.TicketRepo;

public class ChangeTicketStatus {
    
    private TicketRepo ticketRepo;

    private static final Map<Status,List<Status>> allowdedTransitions = Map.of(
        Status.DOING , List.of(Status.TO_DO),
        Status.COMPLETED,List.of(Status.DOING),
        Status.TO_DO,List.of()
    );


    public ChangeTicketStatus(TicketRepo ticketRepoImpl){
        this.ticketRepo = ticketRepoImpl;
    }

    public void execute(String ticketId,String targetStatus){
        try {
        Role r = Role.valueOf(targetStatus);   
        Ticket existingticket = ticketRepo.findTicketById(ticketId);
        Status currentStatus = Status.valueOf(existingticket.getStatus());
        if(!allowdedTransitions.get(currentStatus).contains(r)){
            throw new IllegalStateException("ticket state error");
        }
        existingticket.setStatus(r.name());
        ticketRepo.updateTicket(existingticket);
        }catch (Exception e) {
            throw new IllegalStateException("role not valid");
        }
    }

}
