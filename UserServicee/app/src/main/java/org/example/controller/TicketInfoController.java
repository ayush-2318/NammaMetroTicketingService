package org.example.controller;

import lombok.NonNull;
import org.example.model.TicketInfoDto;
import org.example.response.TicketInfoResponse;
import org.example.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket/v1")
public class TicketInfoController {
    @Autowired
    private TicketService ticketService;
    @GetMapping("/ticketinfo")
    ResponseEntity<List<TicketInfoResponse>> getTicketInfo(@RequestHeader(value = "X-User-Id") @NonNull String userId){
        List<TicketInfoResponse> ticketList=ticketService.getTicketFromUser(userId);
        return new ResponseEntity<List<TicketInfoResponse>>(ticketList, HttpStatus.OK);
    }
}
