package com.fdmgroup.backend_eventhub.eventsession.dto;

import lombok.*;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JoinEventResponse {
    @Getter
    private String token;
    @Setter
    private String videoSource;
    private String roomId;
    private boolean isHost;


}
