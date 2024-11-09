package com.fdmgroup.backend_eventhub.eventsession.dto;

import com.fdmgroup.backend_eventhub.modules.model.Module;
import org.springframework.web.multipart.MultipartFile;

public class CreateContentRequest {
    private long orderNumber;
    private Module module; // can substitute with a module dto request (when saving image/video etc.)
    private MultipartFile file;
    private String type;

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

    public MultipartFile getFile() { return file; }

    public void setFile(MultipartFile file) { this.file = file; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}