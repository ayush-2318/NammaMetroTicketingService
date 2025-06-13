package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.model.TicketInfoDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class JWTParseService {
    private   static  final String SecretKey="357638792F423F4428472B4B6250655368566D597133743677397A2443264629357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    public TicketInfoDto getTicketInfo(String jwtToken){
        Map<Claims,Objects> ticketInfoMap=parseJwt(jwtToken);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.convertValue(ticketInfoMap,TicketInfoDto.class);
    }

    private Map<Claims, Objects> parseJwt(String jwtToken){
        try {
            Claims claims= Jwts.parser()
                    .setSigningKey(SecretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
            return (Map<Claims, Objects>) claims.get("ticketInfo");
        }catch (Exception e){
            throw new RuntimeException("JWT Token Expires");
        }
    }

}
