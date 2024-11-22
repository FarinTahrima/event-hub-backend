package com.fdmgroup.backend_eventhub.modules.model;

import com.fdmgroup.backend_eventhub.modules.dto.InteractiveQuestion;

public record InteractiveQAAction(String SESSION_ID, String TYPE, String QUESTION) {
}
