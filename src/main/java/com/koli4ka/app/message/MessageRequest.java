package com.koli4ka.app.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private UUID senderId;
    private UUID receiverId;
    private String message;


}
