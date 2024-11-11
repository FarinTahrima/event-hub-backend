package com.fdmgroup.backend_eventhub.eventsession.controller;

import com.fdmgroup.backend_eventhub.eventsession.dto.AddContentsToEventRequest;
import com.fdmgroup.backend_eventhub.eventsession.dto.CreateContentRequest;
import com.fdmgroup.backend_eventhub.eventsession.dto.RemoveContentRequest;
import com.fdmgroup.backend_eventhub.eventsession.dto.RemoveContentsFromEventRequest;
import com.fdmgroup.backend_eventhub.eventsession.exceptions.ContentNotFoundException;
import com.fdmgroup.backend_eventhub.eventsession.exceptions.EventNotFoundException;
import com.fdmgroup.backend_eventhub.eventsession.model.Content;
import com.fdmgroup.backend_eventhub.eventsession.service.ContentService;
import com.fdmgroup.backend_eventhub.modules.model.ImageModule;
import com.fdmgroup.backend_eventhub.modules.model.VideoModule;
import com.fdmgroup.backend_eventhub.modules.service.ImageService;
import com.fdmgroup.backend_eventhub.modules.service.VideoService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/content")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1 MB
        maxFileSize = 1024 * 1024 * 5,    // 5 MB
        maxRequestSize = 1024 * 1024 * 5 * 5 // 25 MB
)
public class ContentController {
    private final String EVENT_ID_NOT_FOUND_MESSAGE = "Event ID in request not found";
    private final String NO_CONTENT_FOUND_MESSAGE = "No content found";
    private final String ILLEGAL_PARAMETERS_MESSAGE = "Invalid or missing parameters in request";

    @Autowired
    ContentService contentService;

    @Autowired
    ImageService imageService;

    @Autowired
    VideoService videoService;

    @PostMapping("/add-contents")
    public ResponseEntity<?> createContent(
            @RequestBody AddContentsToEventRequest request) {

        System.out.println(request.toString());
        try {
            for(int i=0; i<request.getContents().length; i++) {
                CreateContentRequest contentToCreate = request.getContents()[i];
                Content content = contentService.saveContent(
                        request.getEventId(),
                        contentToCreate.getOrderNumber(),
                        contentToCreate.getModule()
                );
                //to save images
                if (contentToCreate.getType().equalsIgnoreCase("Image")) {
                    ImageModule imageModule = (ImageModule) contentToCreate.getModule();
                    imageService.createImage(contentToCreate.getFile(), imageModule, content);
                }

                // to save videos
                if (contentToCreate.getType().equalsIgnoreCase("Video")) {
                    VideoModule videoModule = (VideoModule) contentToCreate.getModule();
                    videoService.createVideo(
                            contentToCreate.getFile(),
                            videoModule.getVideoTitle(),
                            videoModule.getDurationSecond(),
                            content.getId()
                    );
                }
            }
        } catch ( EventNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EVENT_ID_NOT_FOUND_MESSAGE);
        } catch ( IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ILLEGAL_PARAMETERS_MESSAGE);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Contents has been added.");
    }

    @PostMapping("/remove-contents")
    public ResponseEntity<?> removeContent(
            @RequestBody RemoveContentsFromEventRequest request) {
        System.out.println(request.toString());
        try {
            for(int i=0; i<request.getContents().length; i++) {
                // to remove videos
                RemoveContentRequest contentToRemove = request.getContents()[i];
                if (contentToRemove.getModuleType().equalsIgnoreCase("Image")) {
                    imageService.deleteImage(contentToRemove.getModuleId());
                }
                if (contentToRemove.getModuleType().equalsIgnoreCase("Video")) {
                    videoService.deleteVideo(contentToRemove.getContentId(), contentToRemove.getModuleId());
                }
                contentService.removeContent(contentToRemove.getContentId());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ILLEGAL_PARAMETERS_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException("Error");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Contents has been removed!");
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
