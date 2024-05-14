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
public class SetGpt4oModelCommandHandler implements TelegramCommandHandler {
    private final ChatGptService chatGptService;
    private final String GPT4o_MESSAGE = """
            Теперь обращаемся к api gpt-4о
            """;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptService.setChatGpt4oModel();
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(GPT4o_MESSAGE)
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.GPT_4o_SET;
    }
}
