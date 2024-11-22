package com.fdmgroup.backend_eventhub.modules.model;

import com.fdmgroup.backend_eventhub.modules.dto.EventPollResponse;

public record LivePollAction(String SESSION_ID, String TYPE, boolean isHost, EventPollResponse poll) {
}
