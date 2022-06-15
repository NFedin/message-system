package com.nikfedin.messagesystem.service;

import com.nikfedin.messagesystem.dto.MessageDto;
import com.nikfedin.messagesystem.entity.Message;
import com.nikfedin.messagesystem.mapper.MessageMapper;
import com.nikfedin.messagesystem.repository.MessageRepository;
import com.nikfedin.messagesystem.request.MessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<MessageDto> getAllMessages(String receiverId) {
        List<Message> messages = messageRepository.findByReceiver(receiverId);
        if (messages.isEmpty()) {
            return Collections.emptyList();
        }
        List<MessageDto> result = messages.stream().map(MessageMapper::toMessageDto).collect(Collectors.toList());
        readAndUpdate(messages);
        return result;
    }

    public MessageDto readMessage(Long id) {
        Optional<Message> optional = messageRepository.findById(id);
        if (optional.isPresent()) {
            Message message = optional.get();
            MessageDto dto = MessageMapper.toMessageDto(message);
            readAndUpdate(Collections.singletonList(message));
            return dto;
        } else {
            return MessageDto.builder().build();
        }
    }

    public List<MessageDto> getAllUnreadMessages(String receiverId) {
        List<Message> messages = messageRepository.findByReceiverAndUnread(receiverId, true);
        if (messages.isEmpty()) {
            return Collections.emptyList();
        }
        List<MessageDto> result = messages.stream().map(MessageMapper::toMessageDto).collect(Collectors.toList());
        readAndUpdate(messages);
        return result;
    }

    public String deleteMessage(String username, Long id) {
        Optional<Message> optional = messageRepository.findById(id);
        if (optional.isPresent()) {
            Message message = optional.get();
            if (username.equals(message.getReceiver()) || username.equals(message.getSender())) {
                messageRepository.deleteById(id);
                return "Message was successfully deleted";
            } else {
                return "Only owner or receiver can delete message";
            }
        } else {
            return "Message not found";
        }

    }

    public MessageDto writeMessage(String sender, MessageRequest messageRequest) {
        Message message = Message.builder()
                .sender(sender)
                .receiver(messageRequest.getReceiver())
                .text(messageRequest.getText())
                .subject(messageRequest.getSubject())
                .creationDate(LocalDateTime.now())
                .unread(true).build();
        return MessageMapper.toMessageDto(messageRepository.save(message));
    }

    private void readAndUpdate(List<Message> messages) {
        messages.forEach(message -> message.setUnread(false));
        messageRepository.saveAllAndFlush(messages);
    }
}
