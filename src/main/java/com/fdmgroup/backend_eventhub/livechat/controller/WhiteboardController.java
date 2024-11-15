package com.fdmgroup.backend_eventhub.livechat.controller;

import com.fdmgroup.backend_eventhub.livechat.models.WhiteboardAction;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhiteboardController {

    SimpMessagingTemplate template;

    public WhiteboardController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/whiteboardAction")
    public void triggerWhiteboardAction(WhiteboardAction action) {
        System.out.println(action);
        template.convertAndSend("/topic/whiteboardAction/" + action.SESSION_ID(), action);

    }
}
