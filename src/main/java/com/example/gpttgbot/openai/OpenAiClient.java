package com.example.gpttgbot.openai;

import com.example.gpttgbot.openai.model.ChatCompletionRequest;
import com.example.gpttgbot.openai.model.ChatCompletionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class OpenAiClient {

    private final String token;

    private final RestTemplate restTemplate;

    public ChatCompletionResponse createChatCompletion(ChatCompletionRequest request) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        httpHeaders.set("Content-type", "application/json");

        HttpEntity<ChatCompletionRequest> httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<ChatCompletionResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ChatCompletionResponse.class
        );

        return responseEntity.getBody();
    }
}
