package com.example.gpttgbot.commands.models;

public enum TelegramCommands {
    START("/start"),
    CLEAR("/clear"),
    GPT_4_SET("/gpt4"),
    GPT_3_SET("/gpt3.5");


    private final String commandValue;

    TelegramCommands(String commandValue) {
        this.commandValue = commandValue;
    }

    public String getCommandValue() {
        return commandValue;
    }
}
