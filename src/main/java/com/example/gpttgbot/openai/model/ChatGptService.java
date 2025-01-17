package com.example.gpttgbot.openai.model;

import com.example.gpttgbot.openai.ChatGptHistory;
import com.example.gpttgbot.openai.OpenAiClient;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    private final OpenAiClient openAiClient;
    private final ChatGptHistory chatGptHistory;

    private final List<GptModels> gptModelsList = new ArrayList<>();
    private String customPromt = "";

    @NonNull
    public String getResponseChatForUser(Long userId, String userTextInput) {
        GptModels gptModel = getGptModel();
        var userHistory = chatGptHistory.getHistoryAndCreate(
                userId,
                Message.builder()
                        .content(userTextInput)
                        .role("user")
                        .build()
        );
        String promt = customPromt.equals("") ? "You are a helpful assistant. Please answer in Russian" : customPromt;
        List<Message> messages = userHistory.chatMessages();
        messages.add(Message.builder()
                .content(promt)
                .role("system")
                .build());
        var request = ChatCompletionRequest.builder()
                .model(gptModel.getDescription())
                .messages(userHistory.chatMessages())
                .build();
        var response = openAiClient.createChatCompletion(request);

        return response.choices().get(0).message().content();
    }

    private GptModels getGptModel() {
        if (gptModelsList.isEmpty()) {
           gptModelsList.add(GptModels.GPT_4o);
        }
        return gptModelsList.get(0);
    }

    public void setChatGpt4Model() {
        if (!gptModelsList.isEmpty()) {
            gptModelsList.remove(0);

        }
        gptModelsList.add(GptModels.GPT_4);
    }

    public void setChatGpt4oModel() {
        if (!gptModelsList.isEmpty()) {
            gptModelsList.remove(0);

        }
        gptModelsList.add(GptModels.GPT_4o);
    }

    public void setChatGpt3Model() {
        if (!gptModelsList.isEmpty()) {
            gptModelsList.remove(0);

        }
        gptModelsList.add(GptModels.GPT_3_5_TURBO);
    }

    public void setCustomPromt(String promt) {
        customPromt = promt;
    }

    public void cleanPromt() {
        customPromt = "";
    }
}
