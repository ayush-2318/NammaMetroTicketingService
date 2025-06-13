package org.example.service;

import org.example.entities.UserTicketInfo;
import org.example.model.TicketInfoDto;
import org.example.repository.UserTicketInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class TicketService {
    @Autowired
    private final UserTicketInfoRepo userTicketInfoRepo;

    @Autowired
    private final JWTParseService jwtParseService;
    public TicketService(UserTicketInfoRepo userTicketInfoRepo, JWTParseService jwtParseService) {
        this.userTicketInfoRepo = userTicketInfoRepo;
        this.jwtParseService = jwtParseService;
    }
    public void saveTicketInfoToDb(String jwtToken){
        TicketInfoDto ticketInfoDto=jwtParseService.getTicketInfo(jwtToken);
        UserTicketInfo userTicketInfo= UserTicketInfo.builder()
                .userId(ticketInfoDto.getUserId())
                .sourceStation(ticketInfoDto.getFrom())
                .destitnationStation(ticketInfoDto.getTo())
                .ticketId(ticketInfoDto.getTicketId())
                .createdAt(LocalDate.now())
                .build();
        userTicketInfoRepo.save(userTicketInfo);
    }
}
