package com.example.gpttgbot.commands.handlers;

import com.example.gpttgbot.commands.TelegramCommandHandler;
import com.example.gpttgbot.commands.models.TelegramCommands;
import com.example.gpttgbot.openai.ChatGptHistory;
import com.example.gpttgbot.openai.model.ChatGptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class SetGpt4ModelCommandHandler implements TelegramCommandHandler {
    private final ChatGptService chatGptService;
    private final String GPT4_MESSAGE = """
            Установлен  gpt-4 для обращения к api
            """;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptService.setChatGpt4Model();
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(GPT4_MESSAGE)
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.GPT_4_SET;
    }
}
