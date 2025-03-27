package com.koli4ka.app.message;

import lombok.Data;
import java.util.UUID;

@Data
public class MessageRequest {
    private UUID senderId;
    private UUID receiverId;
    private String message;
}
