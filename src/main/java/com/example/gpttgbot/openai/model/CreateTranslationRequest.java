package com.example.gpttgbot.openai.model;

import lombok.Builder;

import java.io.File;

@Builder
public record CreateTranslationRequest(
        File audioFile,
        String model,
        String language
) {
}
