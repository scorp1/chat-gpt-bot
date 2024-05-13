package com.example.gpttgbot.openai;

import com.example.gpttgbot.openai.model.ChatCompletionRequest;
import com.example.gpttgbot.openai.model.ChatCompletionResponse;
import com.example.gpttgbot.openai.model.CreateTranslationRequest;
import com.example.gpttgbot.openai.model.TranscriptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    public TranscriptionResponse createTranslation(CreateTranslationRequest request) {
        String url = "https://api.openai.com/v1/audio/translations";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        httpHeaders.set("Content-type", "multipart/form-data");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(request.audioFile()));
        body.add("model", request.model());
        body.add("no-translation", "True");

        var httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<TranscriptionResponse> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, httpEntity, TranscriptionResponse.class);

        return responseEntity.getBody();
    }
}
