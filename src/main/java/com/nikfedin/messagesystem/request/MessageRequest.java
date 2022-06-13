package com.nikfedin.messagesystem.request;

public record MessageRequest(
        String receiver,
        String text,
        String subject) {
}
