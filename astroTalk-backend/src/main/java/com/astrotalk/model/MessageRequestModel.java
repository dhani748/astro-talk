package com.astrotalk.model;

import com.astrotalk.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequestModel {
    @NotNull(message = "Consultation ID is required")
    private Long consultationId;
    @NotBlank(message = "Content is required")
    private String content;
    @NotNull(message = "Message type is required")
    private MessageType messageType;
}
