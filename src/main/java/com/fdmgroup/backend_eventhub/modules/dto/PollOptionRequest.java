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

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPollOptionID(Long pollOptionID) {
        this.pollOptionID = pollOptionID;
    }
}
