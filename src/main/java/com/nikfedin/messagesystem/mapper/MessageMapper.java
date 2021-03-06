package com.nikfedin.messagesystem.mapper;

import com.nikfedin.messagesystem.dto.MessageDto;
import com.nikfedin.messagesystem.entity.Message;

public class MessageMapper {
    public static MessageDto toMessageDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .text(message.getText())
                .subject(message.getSubject())
                .creationDate(message.getCreationDate())
                .unread(message.getUnread())
                .build();
    }

    public static Message toMessage(MessageDto messageDto) {
        return Message.builder()
                .id(messageDto.getId())
                .sender(messageDto.getSender())
                .receiver(messageDto.getReceiver())
                .text(messageDto.getText())
                .subject(messageDto.getSubject())
                .creationDate(messageDto.getCreationDate())
                .build();
    }
}
