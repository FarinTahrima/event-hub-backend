package com.fdmgroup.backend_eventhub.modules.controller;

import com.fdmgroup.backend_eventhub.modules.model.VideoAction;
import com.fdmgroup.backend_eventhub.livechat.service.VideoSyncService;
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

//    @Autowired
//    public VideoSyncController(KafkaTemplate<String, Object> template) {
//        this.kafkaTemplate = template;
//    }

    @MessageMapping("/video")
    public void handleVideoSyncAction(VideoAction action) {

        // uncomment to use Kafka
//        try {
//            kafkaTemplate.send(KafkaConstants.KAFKA_VIDEO_TOPIC, action).get();
//        } catch (InterruptedException | ExecutionException e) {
//            System.out.println("Exception occured while sending video sync message to Kafka: " + e);
//        }
        videoSyncService.sendVideoSyncMessage(action);
    }
}
