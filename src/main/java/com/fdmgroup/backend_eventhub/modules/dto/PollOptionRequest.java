package com.fdmgroup.backend_eventhub.modules.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PollOptionRequest {
    private String description;
    private String fileName;
    private String value;
    private Long pollID;
    private Long pollOptionID;

}
