package com.fdmgroup.backend_eventhub.eventsession.controller;

import com.fdmgroup.backend_eventhub.eventsession.dto.CreateContentRequest;
import com.fdmgroup.backend_eventhub.eventsession.exceptions.ContentNotFoundException;
import com.fdmgroup.backend_eventhub.eventsession.exceptions.EventNotFoundException;
import com.fdmgroup.backend_eventhub.eventsession.model.Content;
import com.fdmgroup.backend_eventhub.eventsession.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    private final String EVENT_ID_NOT_FOUND_MESSAGE = "Event ID in request not found";
    private final String NO_CONTENT_FOUND_MESSAGE = "No content found";
    private final String ILLEGAL_PARAMETERS_MESSAGE = "Invalid or missing parameters in request";

    @Autowired
    ContentService contentService;

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(
            @RequestBody CreateContentRequest createContentRequest) {

        Content content = null;
        System.out.println(createContentRequest.toString());
        try {
            content = contentService.saveContent(
                    createContentRequest.getEventID(),
                    createContentRequest.getOrderNumber(),
                    createContentRequest.getModule()
            );
        } catch ( EventNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EVENT_ID_NOT_FOUND_MESSAGE);
        } catch ( IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ILLEGAL_PARAMETERS_MESSAGE);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(content);
    }

    @GetMapping("/getByEventId/{eventId}")
    public ResponseEntity<?> getContentsByEventId(@PathVariable Long eventId) {
        List<Content> contents = null;
        try {
            contents = contentService.getListOfContentsByEvent(eventId);
        } catch ( EventNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EVENT_ID_NOT_FOUND_MESSAGE);
        } catch (ContentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_CONTENT_FOUND_MESSAGE);
        }
        return ResponseEntity.ok(contents);
    }
}
