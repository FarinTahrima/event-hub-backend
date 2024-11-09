package com.fdmgroup.backend_eventhub.eventsession.dto;

public class RemoveContentsFromEventRequest {
    private long eventId;
    private RemoveContentRequest[] contents;
    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public RemoveContentRequest[] getContents() {
        return contents;
    }

    public void setContents(RemoveContentRequest[] contents) {
        this.contents = contents;
    }
}
