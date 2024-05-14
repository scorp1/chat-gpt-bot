package com.example.gpttgbot.commands.handlers;

import com.example.gpttgbot.commands.TelegramCommandHandler;
import com.example.gpttgbot.commands.models.TelegramCommands;
import com.example.gpttgbot.openai.model.ChatGptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class SetGpt3ModelCommandHandler implements TelegramCommandHandler {
    private final ChatGptService chatGptService;
    private final String GPT3_5_TURBO_MESSAGE = """
            Теперь обращаемся к api gpt-3.5-turbo
            """;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptService.setChatGpt3Model();
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(GPT3_5_TURBO_MESSAGE)
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.GPT_3_SET;
    }
}
