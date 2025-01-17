package com.example.gpttgbot.commands.models;

public enum TelegramCommands {
    START("/start"),
    CLEAR("/clear"),
    GPT_4_SET("/gpt4"),
    GPT_4o_SET("/gpt4o"),
    GPT_3_SET("/gpt3.5"),
    CUSTOM_PROMT("/custom_promt");


    private final String commandValue;

    TelegramCommands(String commandValue) {
        this.commandValue = commandValue;
    }

    public String getCommandValue() {
        return commandValue;
    }
}
