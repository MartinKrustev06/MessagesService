package com.koli4ka.app.message;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID senderId;
    @Column(nullable = false)
    private UUID receiverId;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private LocalDateTime timeStamp;


}
