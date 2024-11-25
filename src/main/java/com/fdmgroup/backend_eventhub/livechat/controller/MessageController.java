package com.fdmgroup.backend_eventhub.livechat.controller;

import com.fdmgroup.backend_eventhub.livechat.models.Message;
import com.fdmgroup.backend_eventhub.livechat.service.MessagePersistenceService;
import com.fdmgroup.backend_eventhub.livechat.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final List<Message> messages = new ArrayList<>();


    private final MessagePersistenceService messagePersistenceService;


    private final MessageSenderService messageSenderService;

    @Autowired
    public MessageController(KafkaTemplate<String, Object> kafkaTemplate, MessagePersistenceService messagePersistenceService, MessageSenderService messageSenderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagePersistenceService = messagePersistenceService;
        this.messageSenderService = messageSenderService;
    }

    @MessageMapping("/chat")
    public void handleChatMessage(Message message, SimpMessageHeaderAccessor headerAccessor) {

        // Check if message sender is authorised to send a message in this live chat.
        // if not, throw an exception
        String partyCode = (String) headerAccessor.getSessionAttributes().get("partyCode");

        if ( !message.getSessionId().equals(partyCode) ) {
            //throw new AccessDeniedException("Unauthorised to send messages in this chat room");
            System.out.println("Live chat token not valid");
        } else {
            System.out.println("Live chat token is also valid!");
        }

        if ( message.getSender() == null || message.getSender().isEmpty() ) {
            message.setSender("anonymous");
        }
        message.setTimeStamp(LocalDateTime.now()); // assign to current time
        System.out.println("Received message: " + message); // Print the received message content


        Message savedMessage = messagePersistenceService.persistMessage(message);

        messageSenderService.sendMessage(savedMessage);


    }

    /*
     * The @SendTo annotation in the provided method is used to specify the destination to which the return value of the method should be sent.
     * In this case, the method handleChatMessage is annotated with @SendTo("/topic/hello"), indicating that the updated list of messages
     * returned by this method should be sent to the specified destination /topic/hello.
     *
     * When a message is sent to the /hello endpoint, the method processes the message, adds it to the messages list,
     * and then returns the updated list of messages. The @SendTo annotation ensures that this updated list is then broadcasted
     * to all subscribers listening on the /topic/hello destination.
     *
     * For example, if a client sends a message to the /app/hello endpoint, the message will be processed by the
     * handleChatMessage method,
     * and the updated list of messages will be sent to all subscribers listening on the /topic/hello destination, allowing them
     * to receive the latest messages in real-time.
     *
     */

    // Returns all messages
    @GetMapping("/api/messages")
    public List<Message> getMessages() {
        return messages;
    }

    @GetMapping("/api/messages/{sessionID}")
    public ResponseEntity<?> getMessagesBySessionID(
            @PathVariable("sessionID") String sessionID) {
        Optional<List<Message>> chatMessagesFromSession =
                messagePersistenceService.findMessagesBySession(sessionID);
        if ( chatMessagesFromSession.isEmpty() ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No messages found for this sessionID");
        } else {
            return ResponseEntity.ok(chatMessagesFromSession.get());
        }

    }

    @GetMapping("/api/clearMessages")
    public void clearMessages() {
        System.out.println("Clearing messages");
        messages.clear();
    }

    @DeleteMapping("/api/messages/{sessionID}")
    public ResponseEntity<Void> deleteMessagesBySessionID(
            @PathVariable("sessionID") String sessionID) {
        messagePersistenceService.deleteMessagesBySession(sessionID);
        return ResponseEntity.ok().build();
    }
}
