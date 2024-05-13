package com.example.gpttgbot.commands;

import com.example.gpttgbot.commands.models.TelegramCommands;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TelegramCommandDispatcher {
    private final List<TelegramCommandHandler> telegramCommandHandlers;

    public BotApiMethod<?> processCommand(Update update) {
        var text = update.getMessage().getText();
        if (!isCommand(update)) {
            throw new IllegalArgumentException("Not command passed: text %s".formatted(text));
        }
        Optional<TelegramCommandHandler> suitedHandeler = telegramCommandHandlers.stream()
                .filter(x -> x.getSupportedCommand().getCommandValue().equals(text))
                .findAny();
        if(suitedHandeler.isEmpty()) {
            return SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("Not supported command: command %s".formatted(text))
                    .build();
        }
        return suitedHandeler.orElseThrow().processCommand(update);
    }

    public boolean isCommand(Update update) {
        return update.getMessage().hasText() && update.getMessage().getText().startsWith("/");
    }
}
