package com.example.gpttgbot.commands.handlers;

import com.example.gpttgbot.commands.TelegramCommandHandler;
import com.example.gpttgbot.commands.models.TelegramCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandHandler implements TelegramCommandHandler {

    private final String HELLO_MESSAGE = """
            Привет %s это бот для использования chatGpt,
            каждое сообщение сохраняется для контекста,
            очистить историю сообщений командой /clear
            """;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(HELLO_MESSAGE.formatted(
                        update.getMessage().getChat().getFirstName()
                ))
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.START;
    }
}
