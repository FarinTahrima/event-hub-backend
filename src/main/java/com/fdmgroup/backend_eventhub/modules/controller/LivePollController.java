package com.fdmgroup.backend_eventhub.modules.controller;

import com.fdmgroup.backend_eventhub.modules.model.LivePollAction;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivePollController {
    SimpMessagingTemplate template;

    public LivePollController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/livePollAction")
    public void triggerLivePollAction(LivePollAction action) {
//        System.out.println(action);
        template.convertAndSend("/topic/livePollAction/" + action.SESSION_ID(), action);

    }
}
