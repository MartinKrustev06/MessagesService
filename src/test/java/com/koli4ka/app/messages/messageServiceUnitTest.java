package com.koli4ka.app.messages;

import com.koli4ka.app.message.Message;
import com.koli4ka.app.message.MessageRepository;
import com.koli4ka.app.message.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith( MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;



    @Test
    void getUUIDS_ShouldReturnListOfUUIDs() {
        UUID senderId = UUID.randomUUID();
        List<UUID> expectedUUIDs = List.of(UUID.randomUUID(), UUID.randomUUID());

        when(messageRepository.getMessageBySenderId(senderId)).thenReturn(List.of());


        List<UUID> result = messageService.getUUIDS(senderId);

        assertEquals(0, result.size());
    }

    @Test
    void getChatTest(){
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Message message1 = new Message();
        message1.setSenderId(senderId);
        message1.setReceiverId(receiverId);
        message1.setMessage("Hello!");
        message1.setTimeStamp(java.time.LocalDateTime.now());

        List<Message> messages = List.of(message1);



        when(messageRepository.findBySenderIdAndReceiverId(any(), any())).thenReturn(messages);

        assertEquals(2, messageService.getChat(senderId, receiverId).size());
    }


}
