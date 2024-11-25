package com.fdmgroup.backend_eventhub.modules.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PollOptionResponse {
    private long pollOptionId;
    private String value;
    private String description;
    private String imageUrl;
    private long voteCount;

}
