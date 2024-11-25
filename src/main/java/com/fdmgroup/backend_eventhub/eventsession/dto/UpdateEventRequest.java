package com.fdmgroup.backend_eventhub.eventsession.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
public class UpdateEventRequest {
    private String eventName;
    private Long accountId;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private Long eventId;

}
