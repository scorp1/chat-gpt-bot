package com.example.gpttgbot.openai.model;

public enum GptModels {
    GPT_4("gpt-4"),
    GPT_3_5_TURBO("gpt-3.5-turbo");

    private final String description;

    GptModels(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
