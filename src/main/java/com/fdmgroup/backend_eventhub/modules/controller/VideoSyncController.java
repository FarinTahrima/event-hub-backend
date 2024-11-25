package com.fdmgroup.backend_eventhub.modules.controller;

import com.fdmgroup.backend_eventhub.livechat.service.VideoSyncService;
import com.fdmgroup.backend_eventhub.modules.model.VideoAction;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class VideoSyncController {
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private final VideoSyncService videoSyncService;

    @MessageMapping("/video")
    public void handleVideoSyncAction(VideoAction action) {

        videoSyncService.sendVideoSyncMessage(action);
    }
}
