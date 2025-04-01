package com.koli4ka.app.web;

import com.koli4ka.app.message.MessageRequest;
import com.koli4ka.app.message.MessageResponse;

import com.koli4ka.app.message.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Премахнат дублиращ се маршрут
    @PostMapping
    public ResponseEntity<Void> saveMessage(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageResponse>> getChat(
            @PathVariable UUID senderId,
            @PathVariable UUID receiverId) {
        List<MessageResponse> chat = messageService.getChat(senderId, receiverId);
        return ResponseEntity.ok(chat);
    }
    @GetMapping("/getChats/{senderId}")
    public ResponseEntity<List<UUID>> getChats(@PathVariable UUID senderId){

        List<UUID> uuids=messageService.getUUIDS(senderId);
        return ResponseEntity.ok(uuids);

    }
}
