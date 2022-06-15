package com.nikfedin.messagesystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequest {
    @NotNull
    @NotEmpty
    private String receiver;
    private String text;
    private String subject;
}
