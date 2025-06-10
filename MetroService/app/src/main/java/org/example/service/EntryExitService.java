package org.example.service;

import com.google.zxing.NotFoundException;
import org.example.entities.TicketInfo;
import org.example.exception.EntryExitException;
import org.example.model.TicketInfoDto;
import org.example.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class EntryExitService {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private QrCodeDecoderService qrCodeDecoderService;

    public boolean isEntryAllowed(String qrCode,String from) throws NotFoundException, IOException {
        TicketInfoDto ticketInfoDto=qrCodeDecoderService.getTicketInfoFromQr(qrCode);
        TicketInfo ticketInfo=ticketService.getTicketInfo(ticketInfoDto.getTicketId())
                .orElseThrow(()-> new EntryExitException("Please Enter Valid Ticket"));
        System.out.println(ticketInfoDto.getFrom());
        System.out.println(ticketInfo.isUsed());
        if(ticketInfo.isUsed()==false&&ticketInfoDto.getFrom().equals(from)){
            ticketInfo.setUsed(true);
            ticketService.updateTicketUsed(ticketInfo);
            return  true;
        }else{
            if(!ticketInfoDto.getFrom().equals(from)){
                 throw new EntryExitException("Source station is not valid");
            }else if(ticketInfo.isUsed()==true){
                throw new EntryExitException("Ticket is already used");
            }
            return false;
        }
    }

    public boolean isExitAllowed(String qrCode,String to) throws NotFoundException, IOException {
        TicketInfoDto ticketInfoDto =qrCodeDecoderService.getTicketInfoFromQr(qrCode);
        if(ticketInfoDto.getTo().equals(to)){
            return true;
        }else{
            return false;
        }
    }
}
