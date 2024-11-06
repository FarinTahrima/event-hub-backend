package com.fdmgroup.backend_eventhub.eventsession.service;

import com.fdmgroup.backend_eventhub.eventsession.exceptions.ContentNotFoundException;
import com.fdmgroup.backend_eventhub.eventsession.exceptions.EventNotFoundException;
import com.fdmgroup.backend_eventhub.eventsession.model.Content;
import com.fdmgroup.backend_eventhub.eventsession.model.Event;
import com.fdmgroup.backend_eventhub.eventsession.repository.IContentRepository;
import com.fdmgroup.backend_eventhub.modules.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    IContentRepository contentRepository;

    @Autowired
    EventService eventService;

    // save content
    public Content saveContent(Long eventId, Long orderNumber, Module module) throws EventNotFoundException {
        Event event = eventService.getEventOrThrow(eventId);
        Content content = new Content();
        content.setOrderNumber(orderNumber);
        content.setModule(module);
        return contentRepository.save(content);
    }

    // get list of contents from an event
    public List<Content> getListOfContentsByEvent(Long eventId) throws EventNotFoundException, ContentNotFoundException {
        Event event = eventService.getEventOrThrow(eventId);
        List<Content> contentList = contentRepository.findByEvent(event);
        if (contentList.isEmpty()) {
            throw new ContentNotFoundException("No content added for this event!");
        }
        return contentList;
    }
}