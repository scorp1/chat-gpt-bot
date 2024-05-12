package com.example.gpttgbot.commands;

import com.example.gpttgbot.commands.models.TelegramCommands;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramCommandHandler {
    BotApiMethod<?> processCommand(Update update);

    TelegramCommands getSupportedCommand();
}
