package com.fdmgroup.backend_eventhub.eventsession.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
public class CreateEventRequest {
    private String eventName;
    private Long accountID;
    private String password;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;

    @Override
    public String toString() {
        return "CreateEventRequest{" +
                "eventName='" + eventName + '\'' +
                ", accountID=" + accountID +
                ", password='" + password + '\'' +
                ", scheduledDate=" + scheduledDate +
                ", scheduledTime=" + scheduledTime +
                '}';
    }
}
