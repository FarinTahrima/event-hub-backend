package com.fdmgroup.backend_eventhub.eventsession.dto;

public class AddContentsToEventRequest {
    private long eventId;
    private CreateContentRequest[] contents;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public CreateContentRequest[] getContents() {
        return contents;
    }

    public void setContents(CreateContentRequest[] contents) {
        this.contents = contents;
    }
}
