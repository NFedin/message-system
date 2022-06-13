package com.nikfedin.messagesystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SimpleMessageDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String sender;

    @JsonProperty
    private String receiver;

    @JsonProperty
    private String text;

    @JsonProperty
    private String subject;

    @JsonProperty
    private Date creationDate;

    @JsonProperty
    private Boolean unread;

}
