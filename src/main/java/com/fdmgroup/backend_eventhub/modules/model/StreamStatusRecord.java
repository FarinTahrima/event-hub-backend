package com.fdmgroup.backend_eventhub.modules.model;

public record StreamStatusRecord(String TYPE, String ID, String SESSION_ID, int VIEWER_COUNT, boolean IS_LIVE) {
}
