package com.example.gpttgbot.openai;

import com.example.gpttgbot.openai.model.ChatHistoryRecord;
import com.example.gpttgbot.openai.model.Message;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatGptHistory {
    private final Map<Long, ChatHistoryRecord> chatHistoryMap = new ConcurrentHashMap<>();
    private Boolean isNeedHistory = true;

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
        if (!isNeedHistory) {
            ChatHistoryRecord chatHistory = new ChatHistoryRecord(new ArrayList<>());
            chatHistory.chatMessages().add(message);
            return chatHistory;
        }
        if (getUserHistory(userId).isEmpty()) {
            createHistory(userId);
        }
        addToHistoryMessage(userId, message);

        return getUserHistory(userId).get();
    }

    public void clearHistory(Long userId) {
        chatHistoryMap.remove(userId);
    }

    public void setNotHistory() {
        isNeedHistory = false;
    }

    public void setNeedHistory() {
        isNeedHistory = true;
    }
}
