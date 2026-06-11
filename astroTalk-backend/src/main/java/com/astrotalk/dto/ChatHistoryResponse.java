package com.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatHistoryResponse {

    private List<MessageResponse> messages;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
