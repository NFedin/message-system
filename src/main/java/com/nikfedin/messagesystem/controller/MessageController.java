package com.nikfedin.messagesystem.controller;

import com.nikfedin.messagesystem.dto.MessageDto;
import com.nikfedin.messagesystem.request.MessageRequest;
import com.nikfedin.messagesystem.response.MessageResponse;
import com.nikfedin.messagesystem.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping(path = "/get-all-messages")
    public ResponseEntity<MessageResponse> getAllMessages() throws AuthenticationException {
        List<MessageDto> result = messageService.getAllMessages(getUsername());
        log.info("Getting all messages for user {}", getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .description("You have " + result.size() + " messages")
                .messages(result)
                .build());

    }

    @GetMapping(path = "/get-all-unread-messages")
    public ResponseEntity<MessageResponse> getAllUnreadMessages() throws AuthenticationException {
        List<MessageDto> result = messageService.getAllUnreadMessages(getUsername());
        log.info("Getting all unread messages for user {}", getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .description("You have " + result.size() + " unread messages")
                .messages(result)
                .build());
    }

    @GetMapping(path = "/read-message/{messageId}")
    public ResponseEntity<MessageResponse> readMessage(@PathVariable Long messageId) {
        MessageDto result = messageService.readMessage(messageId);
        log.info("Read message with messageId = {}", messageId);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .description(Objects.isNull(result.getId()) ? "Message was not found"
                        : "Message was found")
                .messages(Objects.isNull(result.getId()) ? Collections.emptyList()
                        : Collections.singletonList(result))
                .build());
    }

    @DeleteMapping(path = "/delete-message/{messageId}")
    public ResponseEntity<MessageResponse> deleteMessage(@PathVariable Long messageId) throws AuthenticationException {
        String result = messageService.deleteMessage(getUsername(), messageId);
        log.info("Delete message with messageId = {}", messageId);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .description(result)
                .messages(Collections.emptyList())
                .build());
    }

    @PostMapping(path = "/write-message")
    public ResponseEntity<MessageResponse> writeMessage(@Valid @RequestBody MessageRequest messageRequest) throws AuthenticationException {
        MessageDto result = messageService.writeMessage(getUsername(), messageRequest);
        log.info("Write message {}", messageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .description("Message was successfully written")
                .messages(Collections.singletonList(result))
                .build());
    }

    private String getUsername() throws AuthenticationException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            log.error("Unable to determine username");
            throw new AuthenticationException("Unable to determine username");
        }
    }
}
