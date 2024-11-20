package com.fdmgroup.backend_eventhub.livechat.controller;

import com.fdmgroup.backend_eventhub.livechat.models.ModuleAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ModuleActionController {

    SimpMessagingTemplate template;

    Map<String, ModuleAction> currentModuleMap = new ConcurrentHashMap<>();

    public ModuleActionController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/moduleAction")
    public void triggerModuleAction(ModuleAction action) {
        System.out.println(action);
        template.convertAndSend("/topic/moduleAction/" + action.SESSION_ID(), action);
        if ( action.SESSION_ID() != null && !action.SESSION_ID().isEmpty() && !action.TYPE().equals("poll_vote") ) {
            currentModuleMap.put(action.SESSION_ID(), action);
        }
    }

    @GetMapping("/api/moduleAction/{sessionID}")
    public ResponseEntity<?> getCurrentModule(@PathVariable String sessionID) {
        try {
            ModuleAction action = currentModuleMap.get(sessionID);
            return ResponseEntity.ok(action);
        } catch ( Exception e ) {
            System.out.println(sessionID + " is not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sessionID + " is not found");
        }
    }
}
