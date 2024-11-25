package com.fdmgroup.backend_eventhub.modules.controller;

import com.fdmgroup.backend_eventhub.modules.model.InteractiveQAAction;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InteractiveQAController {
    SimpMessagingTemplate template;

    public InteractiveQAController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/interactiveQAAction")
    public void triggerInteractiveQAAction(InteractiveQAAction action) {
//        System.out.println(action);
        template.convertAndSend("/topic/interactiveQAAction/" + action.SESSION_ID(), action);

    }
}
