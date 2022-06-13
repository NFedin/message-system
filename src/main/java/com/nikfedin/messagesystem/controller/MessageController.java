package com.nikfedin.messagesystem.controller;

import com.nikfedin.messagesystem.dto.MessageDto;
import com.nikfedin.messagesystem.request.MessageRequest;
import com.nikfedin.messagesystem.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/v1/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping(path = "/get-all-messages")
    public ResponseEntity<Object> getAllMessages() throws AuthenticationException {
        List<MessageDto> result = messageService.getAllMessages(getUsername());
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Messages not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path = "/get-all-unread-messages")
    public ResponseEntity<Object> getAllUnreadMessages() throws AuthenticationException {
        List<MessageDto> result = messageService.getAllUnreadMessages(getUsername());
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Messages not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path = "/read-message/{messageId}")
    public ResponseEntity<Object> readMessage(@PathVariable Long messageId) {
        MessageDto result = messageService.readMessage(messageId);
        if (Objects.isNull(result.getId())) {
            return ResponseEntity.status(HttpStatus.OK).body("Messages not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path = "/delete-message/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) throws AuthenticationException {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.deleteMessage(getUsername(), messageId));
    }

    @PostMapping(path = "/write-message")
    public ResponseEntity<String> writeMessage(@RequestBody MessageRequest messageRequest) throws AuthenticationException {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.writeMessage(getUsername(), messageRequest));
    }

    private String getUsername() throws AuthenticationException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            throw new AuthenticationException("Unable to determine username");
        }
    }
}
