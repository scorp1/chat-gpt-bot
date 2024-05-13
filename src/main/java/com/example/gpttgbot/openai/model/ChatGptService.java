package com.example.gpttgbot.openai.model;

import com.example.gpttgbot.openai.ChatGptHistory;
import com.example.gpttgbot.openai.OpenAiClient;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatGptService {
    private final OpenAiClient openAiClient;
    private final ChatGptHistory chatGptHistory;

    private final List<GptModels> gptModelsList = new ArrayList<>();

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
        List<Message> messages = userHistory.chatMessages();
        messages.add(Message.builder()
                .content("You are a helpful assistant. Please answer in Russian")
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
}
