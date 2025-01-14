package com.example.gpttgbot.commands.handlers;

import com.example.gpttgbot.commands.TelegramCommandHandler;
import com.example.gpttgbot.commands.models.TelegramCommands;
import com.example.gpttgbot.openai.ChatGptHistory;
import com.example.gpttgbot.openai.model.ChatGptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class ClearHistoryCommandHandler implements TelegramCommandHandler {

    private final ChatGptHistory chatGptHistoryService;
    private final ChatGptService chatGptService;
    private final String CLEAN_HISTORY_MESSAGE = """
            Ваша истории сообщений с chatGpt очищена.
            """;
    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptHistoryService.clearHistory(update.getMessage().getChatId());
        chatGptHistoryService.setNeedHistory();
        chatGptService.cleanPromt();

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(CLEAN_HISTORY_MESSAGE)
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.CLEAR;
    }
}
