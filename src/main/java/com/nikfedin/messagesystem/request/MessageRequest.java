package com.nikfedin.messagesystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequest {
    private String receiver;
    private String text;
    private String subject;
}
