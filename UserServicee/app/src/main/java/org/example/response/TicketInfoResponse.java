package org.example.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.UserTicketInfo;

public class TicketInfoResponse {
    @JsonProperty("ticketId")
    private String ticketId;
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("userId")
    private String userId;
    public TicketInfoResponse(UserTicketInfo userTicketInfo){
        this.userId=userTicketInfo.getUserId();
        this.ticketId=userTicketInfo.getTicketId();
        this.from=userTicketInfo.getSourceStation();
        this.to=userTicketInfo.getDestitnationStation();
    }
}
