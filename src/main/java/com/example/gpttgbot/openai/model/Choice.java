package com.example.gpttgbot.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Choice(@JsonProperty("message") Message message){
}
