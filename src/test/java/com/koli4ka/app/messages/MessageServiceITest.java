package com.koli4ka.app.messages;

import com.koli4ka.app.message.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageServiceITest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    private UUID senderId;
    private UUID receiverId;

    @BeforeEach
    void setUp() {
        senderId = UUID.randomUUID();
        receiverId = UUID.randomUUID();
    }

    @Test
    void saveMessage_ShouldSaveToDatabase() {
        MessageRequest request = new MessageRequest();
        request.setSenderId(senderId);
        request.setReceiverId(receiverId);
        request.setMessage("Hello!");

        messageService.saveMessage(request);

        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        assertEquals(1, messages.size());
        assertEquals("Hello!", messages.get(0).getMessage());
    }

    @Test
    void getChat_ShouldReturnMessagesInOrder() {
        saveTestMessage(senderId, receiverId, "First message");
        saveTestMessage(receiverId, senderId, "Reply message");

        List<MessageResponse> chat = messageService.getChat(senderId, receiverId);

        assertEquals(2, chat.size());
        assertEquals("First message", chat.get(0).getMessage());
        assertEquals("Reply message", chat.get(1).getMessage());
    }

    @Test
    void getUUIDS_ShouldReturnChatPartners() {
        UUID thirdUser = UUID.randomUUID();
        saveTestMessage(senderId, receiverId, "Hello!");
        saveTestMessage(senderId, thirdUser, "Hi there!");

        List<UUID> chatPartners = messageService.getUUIDS(senderId);

        assertEquals(2, chatPartners.size());
        assertTrue(chatPartners.contains(receiverId));
        assertTrue(chatPartners.contains(thirdUser));
    }

    private void saveTestMessage(UUID sender, UUID receiver, String text) {
        Message message = new Message();
        message.setSenderId(sender);
        message.setReceiverId(receiver);
        message.setMessage(text);
        message.setTimeStamp(java.time.LocalDateTime.now());
        messageRepository.save(message);
    }
}
