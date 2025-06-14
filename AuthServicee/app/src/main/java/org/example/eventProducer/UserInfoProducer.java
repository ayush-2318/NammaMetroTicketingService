package org.example.eventProducer;

import org.example.model.UserInfoProducerDto;
import org.example.serializer.UserInfoSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {
    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

   private final KafkaTemplate<String, UserInfoProducerDto> kafkaTemplate;

    public UserInfoProducer(@Qualifier("userInfoKafkaTemplate") KafkaTemplate<String,UserInfoProducerDto> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    @Async
    public void sendEventTokafka(UserInfoProducerDto userInfoProducerDto){
        Message<UserInfoProducerDto>message= MessageBuilder
                .withPayload(userInfoProducerDto)
                .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);
    }

}
