package com.example.gpttgbot.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiConfiguration {

    @Bean
    public OpenAiClient openAiClient(
            @Value("${gpt.token}") String token,
            RestTemplateBuilder restTemplateBuilder
    ) {
        return new OpenAiClient(token, restTemplateBuilder.build());
    }


}
