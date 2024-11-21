package com.fdmgroup.backend_eventhub.modules.model;

import java.time.LocalDateTime;

public record ModuleAction(String ID, String TYPE, String SESSION_ID, String SENDER, LocalDateTime TIMESTAMP,
                           String TITLE, String CONTENT, String IMAGE_URL, boolean IS_LIVE) {
}
