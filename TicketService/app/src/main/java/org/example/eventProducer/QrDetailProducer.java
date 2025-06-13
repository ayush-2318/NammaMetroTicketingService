package org.example.eventProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QrDetailProducer {
    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    QrDetailProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }


    @Async
    public void sendEventToKafka(String eventData){
        Message<String> message= MessageBuilder
                .withPayload(eventData)
                .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);
    }
}
