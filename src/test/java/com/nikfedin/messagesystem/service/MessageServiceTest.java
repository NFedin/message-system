package com.nikfedin.messagesystem.service;

import com.nikfedin.messagesystem.dto.MessageDto;
import com.nikfedin.messagesystem.entity.Message;
import com.nikfedin.messagesystem.mapper.MessageMapper;
import com.nikfedin.messagesystem.repository.MessageRepository;
import com.nikfedin.messagesystem.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageRepository repository;

    @InjectMocks
    private MessageService service;

    private Message message;

    private Message unreadMessage;

    private List<Message> messageList;

    private List<MessageDto> messageDtoList;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(MessageRepository.class);
        service = new MessageService(repository);
        message = Message.builder()
                .id(1L)
                .sender("Clint Eastwood")
                .receiver("Chuck Norris")
                .subject("subject")
                .text("text")
                .creationDate(OffsetDateTime.now())
                .unread(true)
                .build();
        unreadMessage = Message.builder()
                .id(2L)
                .sender("Clint Eastwood")
                .receiver("Chuck Norris")
                .subject("subject")
                .text("text")
                .creationDate(OffsetDateTime.now())
                .unread(false)
                .build();
        messageList = new ArrayList<>();
        messageList.add(message);
        messageList.add(unreadMessage);
        messageDtoList = new ArrayList<>();
        messageDtoList.add(MessageMapper.toMessageDto(message));
        messageDtoList.add(MessageMapper.toMessageDto(unreadMessage));
    }

    @DisplayName("Test for getAllMessages method")
    @Test
    void getAllMessages() {
        given(repository.findByReceiver("Chuck Norris")).willReturn(messageList);
        List<MessageDto> result = service.getAllMessages("Chuck Norris");
        assertEquals(result, messageDtoList, "Check result of method");
    }

    @DisplayName("Test for readMessage method")
    @Test
    void readMessage() {
        given(repository.findById(1L)).willReturn(Optional.of(message));
        MessageDto result = service.readMessage(1L);
        message.setUnread(true);
        assertEquals(result, MessageMapper.toMessageDto(message), "Check result of method");
    }

    @DisplayName("Test for getAllUnreadMessages method")
    @Test
    void getAllUnreadMessages() {
        given(repository.findByReceiverAndUnread("Chuck Norris", true))
                .willReturn(Collections.singletonList(unreadMessage));
        List<MessageDto> result = service.getAllUnreadMessages("Chuck Norris");
        assertEquals(result, Collections.singletonList(MessageMapper.toMessageDto(unreadMessage)), "Check result of method");
    }

    @DisplayName("Test for deleteMessage method")
    @Test
    void deleteMessage() {
        given(repository.findById(1L)).willReturn(Optional.of(message));
        service.deleteMessage("Chuck Norris", 1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @DisplayName("Test for writeMessage method")
    @Test
    void writeMessage() {
        given(repository.save(any(Message.class))).willReturn(message);
        MessageDto result = service.writeMessage("Clint Eastwood",
                new MessageRequest("Chuck Norris", "text", "subject"));
        assertEquals(result, MessageMapper.toMessageDto(message), "Check result of method");
    }
}