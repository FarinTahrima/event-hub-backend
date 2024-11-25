package com.fdmgroup.backend_eventhub.eventsession.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateEventResponse {

    private String token;
    private String videoSource;
    private String code;
    private boolean isHost;

    public CreateEventResponse(String token, String videoSource, String code, boolean isHost) {
        this.token = token;
        this.videoSource = videoSource;
        this.code = code;
        this.isHost = isHost;
    }

    public CreateEventResponse() {
    }


    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    @Override
    public String toString() {
        return "CreateEventResponse{" +
                "token='" + token + '\'' +
                ", videoSource='" + videoSource + '\'' +
                ", code='" + code + '\'' +
                ", isHost=" + isHost +
                '}';
    }
}
