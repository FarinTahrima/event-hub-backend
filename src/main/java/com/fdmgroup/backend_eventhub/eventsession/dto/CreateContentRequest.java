package com.fdmgroup.backend_eventhub.eventsession.dto;

import com.fdmgroup.backend_eventhub.modules.model.Module;

public class CreateContentRequest {
    private long eventID;
    private long orderNumber;
    private Module module; // can substitute with a module dto request (when saving image/video etc.)

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}