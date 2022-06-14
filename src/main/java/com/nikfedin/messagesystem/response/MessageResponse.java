package com.nikfedin.messagesystem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nikfedin.messagesystem.dto.MessageDto;
import lombok.Builder;

import java.util.List;

@Builder
public record MessageResponse(
        @JsonProperty
        String description,
        @JsonProperty
        List<MessageDto> messages
) {
}
