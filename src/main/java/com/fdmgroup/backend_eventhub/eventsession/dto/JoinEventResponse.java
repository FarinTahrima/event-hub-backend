package com.fdmgroup.backend_eventhub.eventsession.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Getter
@Setter
public class JoinEventResponse {
    @Getter
    private String token;
    @Setter
    private String videoSource;
    private String roomId;
    private boolean isHost;

    public JoinEventResponse(String token, String videoSource, String roomId, boolean isHost) {
        this.token = token;
        this.videoSource = videoSource;
        this.roomId = roomId;
        this.isHost = isHost;
    }

//    public JoinEventResponse() {
//    }

//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getVideoSource() {
//        return videoSource;
//    }
//
//    public String getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }
//
//    public boolean isHost() {
//        return isHost;
//    }
//
//    public void setHost(boolean host) {
//        isHost = host;
//    }
}
