package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.UserTicketInfo;
import org.example.model.TicketInfoDto;
import org.example.repository.UserTicketInfoRepo;
import org.example.response.TicketInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private List<UserTicketInfo>getTicketInfo(String userId){
        return userTicketInfoRepo.findByUserId(userId);
    }

    public List<TicketInfoResponse> getTicketFromUser(String userId){
        List<UserTicketInfo> ticket=getTicketInfo(userId);
        if(ticket.size()==0){
            throw new RuntimeException("Ticket not found");
        }
        List<TicketInfoResponse> ticketInfoDtos=ticket.stream()
                .map(TicketInfoResponse::new)
                .collect(Collectors.toList());
        return ticketInfoDtos;
    }
}
