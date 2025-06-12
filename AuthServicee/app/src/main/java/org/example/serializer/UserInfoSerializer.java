package org.example.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.example.model.UserInfoProducerDto;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoProducerDto> {

    private final ObjectMapper objectMapper;

    public UserInfoSerializer(){
        this.objectMapper=new ObjectMapper();
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String arg0, UserInfoProducerDto arg1) {
        byte[] retVal=null;
        try {
            retVal=objectMapper.writeValueAsBytes(arg1);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return retVal;
    }



    @Override
    public void close() {

    }
}
