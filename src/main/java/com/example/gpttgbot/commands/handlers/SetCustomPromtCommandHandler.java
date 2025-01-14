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
public class SetCustomPromtCommandHandler implements TelegramCommandHandler {
    private final ChatGptService chatGptService;
    private final ChatGptHistory chatGptHistory;
    private final String GPT_MESSAGE = """
            Установлен промт для получения разбора составов косметических средств.
            Пишите компоненты через запятую. Будет получено полное описание по каждому
            компоненту.
            """;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptService.setCustomPromt("You are a helpful assistant. Please answer in Russian, " +
                "I will give you a list of ingredients, creams or other cosmetics, " +
                "and you will write down each component and the recommended components that are harmful to health," +
                "if such a component does not exist, write that there is no such component, check the spelling");
        chatGptHistory.setNotHistory();
        return SendMessage.builder().chatId(update.getMessage().getChatId())
                .text(GPT_MESSAGE)
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.CUSTOM_PROMT;
    }
}
