package com.fdmgroup.backend_eventhub.modules.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EventPollResponse {
    private long pollId;
    private String pollQuestion;
    private List<PollOptionResponse> pollOptionList;
    private boolean voted;
    private PollOptionResponse selectedPollOption;


}
