package org.example.auth;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.model.UserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TicketInfoProducerConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id1}")
    private String consumerGroupId;

    @Bean
    public ConsumerFactory<String, String> ticketInfoConsumerFactory(){
        Map<String,Object> configprops=new HashMap<>();
        configprops.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configprops.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configprops.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        configprops.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");

        return new DefaultKafkaConsumerFactory<>(configprops);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> ticketListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String>factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ticketInfoConsumerFactory());
        return  factory;

    }
}
