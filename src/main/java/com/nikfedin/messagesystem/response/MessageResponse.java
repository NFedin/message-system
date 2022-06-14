package com.nikfedin.messagesystem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nikfedin.messagesystem.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    @JsonProperty
    private String description;
    @JsonProperty
    private List<MessageDto> messages;
}
