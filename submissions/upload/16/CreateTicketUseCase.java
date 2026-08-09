package com.example.TicketingService.Domain.UseCase.Ticket;

import java.util.ArrayList;
import java.util.List;

import com.example.TicketingService.Domain.Auth.AuthProvider;
import com.example.TicketingService.Domain.Entities.Photo;
import com.example.TicketingService.Domain.Entities.Ticket;
import com.example.TicketingService.Domain.Entities.Enums.Priority;
import com.example.TicketingService.Domain.Entities.Enums.Status;
import com.example.TicketingService.Domain.Entities.Enums.TicketCategory;
import com.example.TicketingService.Domain.Repo.TicketRepo;
import com.example.TicketingService.Domain.UseCase.Input.TicketRequests.CreateTicketRequest;
import com.example.TicketingService.Domain.UseCase.Input.TicketRequests.PhotoRequest;
import com.example.TicketingService.Domain.UseCase.Photo.IUploadPhotoUseCase;



public class CreateTicketUseCase {
    private TicketRepo ticketRepository;   
    private AuthProvider authProvider;
    private IUploadPhotoUseCase uploadPhotoImpl;

    public CreateTicketUseCase(TicketRepo ticketRepoImpl,AuthProvider authProviderImpl,IUploadPhotoUseCase uploadPhotoImpl){
        this.ticketRepository = ticketRepoImpl;
        this.authProvider=authProviderImpl;
        this.uploadPhotoImpl = uploadPhotoImpl;
    }

    public void execute(CreateTicketRequest ticketRequest) throws Exception{

        List<Photo> photos = new ArrayList<Photo>();

        for(PhotoRequest file : ticketRequest.photoRequest()){

            String path = uploadPhotoImpl.execute(authProvider.getConnectedUser().getId(),file);

            photos.add(new Photo.Builder().setFileName(file.fileName()).setPath(path).setSize(file.size()).Build());

        }

        System.out.println(ticketRequest.detailsCommand().category());

        Ticket  t = new Ticket.Builder()

                            .setDescription(ticketRequest.detailsCommand().description())

                            .setTitle(ticketRequest.detailsCommand().title())

                            .setStatus(Status.TO_DO.name())
                            
                            .setPhotos(photos)

                            .setRequester(authProvider.getConnectedUser())

                            .setStage(Priority.valueOf(ticketRequest.detailsCommand().priority()))

                            .setCategory(TicketCategory.valueOf(ticketRequest.detailsCommand().category()))

                            .Build();
                                    
        ticketRepository.createNewTicket(t,photos);
 
    }

}