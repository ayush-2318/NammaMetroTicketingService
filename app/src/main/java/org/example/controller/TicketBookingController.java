package org.example.controller;

import org.example.request.TicketRequest;
import org.example.service.TicketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TicketBookingController {
    @Autowired
    private TicketPriceService ticketPriceService;
    @PostMapping("/bookTicket")
    public Mono<ResponseEntity> TicketBooking(@RequestBody TicketRequest ticketRequest){
        Mono<Boolean> isPaymentSuccessful=ticketPriceService.isPaymentSuccessful(ticketRequest);
    return   isPaymentSuccessful.map(
                success->{
                    if (success){
                        return new ResponseEntity<>("PaymentDone",HttpStatus.OK);
                    }else {
                        return new ResponseEntity<>("Not done",HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
        );

    }
}
