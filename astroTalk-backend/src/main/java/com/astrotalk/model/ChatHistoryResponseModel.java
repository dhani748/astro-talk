package com.astrotalk.model;

import com.astrotalk.model.MessageResponseModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatHistoryResponseModel {
    private List<MessageResponseModel> messages;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
