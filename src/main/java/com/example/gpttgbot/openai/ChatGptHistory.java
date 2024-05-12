package com.example.gpttgbot.openai;

import com.example.gpttgbot.openai.model.ChatHistoryRecord;
import com.example.gpttgbot.openai.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ChatGptHistory {
    private final Map<Long, ChatHistoryRecord> chatHistoryMap = new ConcurrentHashMap<>();

    public Optional<ChatHistoryRecord> getUserHistory(Long userId) {
        return Optional.ofNullable(chatHistoryMap.get(userId));
    }

    public void createHistory(Long userId) {
        chatHistoryMap.put(userId, new ChatHistoryRecord(new ArrayList<>()));
    }

    public void addToHistoryMessage(Long userId, Message message) {
        ChatHistoryRecord historyRecord = chatHistoryMap.get(userId);
        historyRecord.chatMessages().add(message);
    }

    public ChatHistoryRecord getHistoryAndCreate(Long userId, Message message) {
        if (getUserHistory(userId).isEmpty()) {
            createHistory(userId);
        }
        addToHistoryMessage(userId, message);

        return getUserHistory(userId).get();
    }

    public void clearHistory(Long userId) {
        chatHistoryMap.remove(userId);
    }
}
