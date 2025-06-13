package org.example.consumer;

import org.example.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TicketInfoConsumer {
    @Autowired
    private final TicketService ticketService;

    public TicketInfoConsumer(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @KafkaListener(topics="${spring.kafka.topic.name1}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String eventData){
        try {
            ticketService.saveTicketInfoToDb(eventData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
