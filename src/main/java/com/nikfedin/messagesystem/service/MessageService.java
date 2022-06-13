package com.nikfedin.messagesystem.service;

import com.nikfedin.messagesystem.dto.SimpleMessageDto;
import com.nikfedin.messagesystem.entity.Message;
import com.nikfedin.messagesystem.mapper.MessageMapper;
import com.nikfedin.messagesystem.repository.MessageRepository;
import com.nikfedin.messagesystem.request.MessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<SimpleMessageDto> getAllMessages(String receiverId) {
        List<Message> messages = messageRepository.findByReceiver(receiverId);
        if (messages.isEmpty()) {
            return Collections.emptyList();
        }
        List<SimpleMessageDto> result = messages.stream().map(MessageMapper::toMessageDto).collect(Collectors.toList());
        readAndUpdate(messages);
        return result;
    }

    public SimpleMessageDto readMessage(Long id) {
        Optional<Message> optional = messageRepository.findById(id);
        if (optional.isPresent()) {
            Message message = optional.get();
            SimpleMessageDto dto = MessageMapper.toMessageDto(message);
            readAndUpdate(List.of(message));
            return dto;
        } else {
            return SimpleMessageDto.builder().build();
        }
    }

    public List<SimpleMessageDto> getAllUnreadMessages(String receiverId) {
        List<Message> messages = messageRepository.findByReceiverAndUnread(receiverId, true);
        if (messages.isEmpty()) {
            return Collections.emptyList();
        }
        List<SimpleMessageDto> result = messages.stream().map(MessageMapper::toMessageDto).collect(Collectors.toList());
        readAndUpdate(messages);
        return result;
    }

    public String deleteMessage(String username, Long id) {
        Optional<Message> optional = messageRepository.findById(id);
        if (optional.isPresent()) {
            Message message = optional.get();
            if (username.equals(message.getReceiver()) || username.equals(message.getSender())) {
                messageRepository.deleteById(id);
            } else {
                return "Only owner or receiver can delete message";
            }
            return "Message was successfully deleted";
        } else {
            return "Message not found";
        }

    }

    public String writeMessage(String sender, MessageRequest messageRequest) {
        Message message = Message.builder()
                .sender(sender)
                .receiver(messageRequest.receiver())
                .text(messageRequest.text())
                .subject(messageRequest.subject())
                .creationDate(new Date())
                .unread(true).build();
        messageRepository.save(message);
        return "Message was successfully written";
    }

    private void readAndUpdate(List<Message> messages) {
        messages.forEach(message -> message.setUnread(false));
        messageRepository.saveAllAndFlush(messages);
    }
}
