package com.example.gpttgbot.openai.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatHistoryRecord(List<Message> chatMessages) {
}
