package org.example.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.model.UserInfoDto;

import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfoDto> {
    ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public UserInfoDto deserialize(String arg0, byte[] arg1) {
        UserInfoDto userInfoDto=null;
        try {
            userInfoDto=objectMapper.readValue(arg1,UserInfoDto.class);
        }catch (Exception ex){
            System.out.println("Not able to deserialise");
        }
        return userInfoDto;
    }

    @Override
    public void close() {

    }
}
