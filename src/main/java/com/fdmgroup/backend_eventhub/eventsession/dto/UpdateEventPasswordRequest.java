package com.fdmgroup.backend_eventhub.eventsession.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UpdateEventPasswordRequest {
    private String password;
    private Long accountId;
    private Long eventId;

}
