package com.example.gpttgbot.openai;

import com.example.gpttgbot.openai.model.CreateTranslationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@AllArgsConstructor
public class TranscribeVoiceService {
    private final OpenAiClient openAiClient;

    public String transcribe(File audioFile) {
        var response = openAiClient.createTranslation(CreateTranslationRequest.builder()
                        .audioFile(audioFile)
                        .model("whisper-1")
                        .language("ru")
                .build());
        return response.text();
    }

}
