package com.fdmgroup.backend_eventhub.livechat.models;

public record WhiteboardAction(String SESSION_ID, String TYPE, long X, long Y, String COLOR, long LINE_WIDTH) {
}
