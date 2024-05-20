package com.example.gpttgbot.telegram;

import com.example.gpttgbot.commands.TelegramCommandDispatcher;
import com.example.gpttgbot.openai.OpenAiClient;
import com.example.gpttgbot.openai.TranscribeVoiceService;
import com.example.gpttgbot.openai.model.ChatGptService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Slf4j
@Component
public class TelegramBot  extends TelegramLongPollingBot {

    private final ChatGptService chatGptService;
    private final TelegramCommandDispatcher telegramCommandDispatcher;
    private final TelegramFileService telegramFileService;

    private final TranscribeVoiceService transcribeVoiceService;

    public TelegramBot(@Value("${bot.token}")String botToken,
                       ChatGptService chatGptService,
                       TelegramCommandDispatcher telegramCommandDispatcher,
                       TelegramFileService telegramFileService,
                       TranscribeVoiceService transcribeVoiceService) {
        super(new DefaultBotOptions(), botToken);
        this.chatGptService = chatGptService;
        this.telegramCommandDispatcher = telegramCommandDispatcher;
        this.telegramFileService = telegramFileService;
        this.transcribeVoiceService = transcribeVoiceService;
    }
    @Override
    public void onUpdateReceived(Update update) {
        try {
            var methods = processUpdate(update);
            methods.forEach(x -> {
                try {
                    sendApiMethod(x);
                }catch (TelegramApiException e) {
                    throw new RuntimeException();
                }
            });
        }catch (Exception e) {
            log.error("Error while processing update: ", e);
            sendUserErrorMessage(update.getMessage().getChatId());
        }


    }

    private List<BotApiMethod<?>> processUpdate(Update update) {
        if (telegramCommandDispatcher.isCommand(update)) {
            return List.of(telegramCommandDispatcher.processCommand(update));
        }
        if (update.hasMessage() && update.getMessage().hasText()) {

            return List.of(getMessageResponseFromGpt(update.getMessage().getChatId(), update.getMessage().getText()));
        }
        if (update.hasMessage() && update.getMessage().hasVoice()) {
            File file = telegramFileService.getFile(update.getMessage().getVoice().getFileId());

            return List.of(getMessageResponseFromGpt(
                    update.getMessage().getChatId(),
                    transcribeVoiceService.transcribe(file)));
        }
        return List.of();
    }

    public SendMessage getMessageResponseFromGpt(Long chatId, String text) {
        log.info("Send message from telegram user: " + text);
        var gptGeneratedText = chatGptService.getResponseChatForUser(chatId, text);
        return new SendMessage(chatId.toString(), gptGeneratedText);
    }

    @SneakyThrows
    private void sendUserErrorMessage(Long userId) {
        sendApiMethod(SendMessage.builder()
                .chatId(userId)
                .text("Произошла ошибка, повторите позже")
                .build());
    }

//    public String getMessageResponse(String text) {
//        String textResponse = "";
//        switch (text) {
//            case "привет":
//                textResponse = "задаров";
//                break;
//            case "как дела?":
//                textResponse = "норм, сам как ?";
//                break;
//            default:
//                var chatCompletionResponse = openAiClient.createChatCompletion(text);
//                textResponse = chatCompletionResponse.choices().get(0).message().content();
//        }
//        return textResponse;
//    }

    @Override
    public String getBotUsername() {
        String botName = "blp_gpt_bot";
        return botName;
    }
}
