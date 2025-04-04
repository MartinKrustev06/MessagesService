package com.koli4ka.app.message;

import com.koli4ka.app.message.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public void saveMessage(MessageRequest messageRequest) {
        Message message = new Message();
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setMessage(messageRequest.getMessage());
        message.setTimeStamp(LocalDateTime.now());

        messageRepository.save(message);
    }


    public List<MessageResponse> getChat(UUID user1, UUID user2) {
        List<Message> messages1 = messageRepository.findBySenderIdAndReceiverId(user1, user2);
        List<Message> messages2 = messageRepository.findBySenderIdAndReceiverId(user2, user1);

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messages1);
        allMessages.addAll(messages2);


        allMessages.sort((m1, m2) -> m1.getTimeStamp().compareTo(m2.getTimeStamp()));


        List<MessageResponse> chatMessages = new ArrayList<>();
        for (Message message : allMessages) {
            chatMessages.add(mapToResponse(message));
        }

        return chatMessages;
    }


    private MessageResponse mapToResponse(Message message) {
        MessageResponse response = new MessageResponse();
        response.setSenderId(message.getSenderId());
        response.setReceiverId(message.getReceiverId());
        response.setMessage(message.getMessage());

        return response;
    }

    public List<UUID> getUUIDS(UUID senderId) {

        List<Message> messageBySenderId = messageRepository.getMessageBySenderId(senderId);
        List<Message> messageByReceiverId = messageRepository.getMessageByReceiverId(senderId);
        List<UUID> uuids = new ArrayList<>();
        for (Message message : messageBySenderId) {
            if(uuids.contains(message.getReceiverId())){
                continue;
            }
            uuids.add(message.getReceiverId());
        }
       for  (Message message : messageByReceiverId) {
            if(uuids.contains(message.getSenderId())){
                continue;
            }
            uuids.add(message.getSenderId());
        }

        System.out.println(uuids);
        return uuids;
    }
}
