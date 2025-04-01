package com.koli4ka.app.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koli4ka.app.message.MessageService;
import com.koli4ka.app.message.MessageRequest;
import com.koli4ka.app.web.MessageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveMessage_ShouldReturnCreatedStatus() throws Exception {
        MessageRequest messageRequest = new MessageRequest(UUID.randomUUID(), UUID.randomUUID(), "Hello!");
        doNothing().when(messageService).saveMessage(any(MessageRequest.class));

        mockMvc.perform(post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isCreated());
    }
}