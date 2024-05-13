package com.example.gpttgbot.telegram;

import com.example.gpttgbot.commands.TelegramCommandDispatcher;
import com.example.gpttgbot.openai.TranscribeVoiceService;
import com.example.gpttgbot.openai.model.ChatGptService;
import com.example.gpttgbot.telegram.TelegramBot;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfiguration {

//    @Bean
//    public TelegramBot telegramBot(
//            @Value("${bot.token}") String botToken,
//            TelegramBotsApi telegramBotsApi,
//            ChatGptService chatGptService,
//            TelegramCommandDispatcher telegramCommandDispatcher,
//            TelegramFileService telegramFileService,
//            TranscribeVoiceService transcribeVoiceService) {
//        var botOptions = new DefaultBotOptions();
//        var bot = new TelegramBot(botOptions, botToken, chatGptService, telegramCommandDispatcher, telegramFileService, transcribeVoiceService);
//        try {
//            telegramBotsApi.registerBot(bot);
//        }catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//        return bot;
//    }

    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) {
        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);

        return telegramBotsApi;
    }
}
