package org.example.consumer;

import org.example.model.UserInfoDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserInfoconsumer {
    @Autowired
    private  UserService userService;



    @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}",containerFactory = "kafkaListenerContainerFactory")
    public void listen(UserInfoDto eventData){
        try {
            userService.createOrUpdate(eventData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
