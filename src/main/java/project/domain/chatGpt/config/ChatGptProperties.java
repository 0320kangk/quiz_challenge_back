package project.domain.chatGpt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ChatGptProperties {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.api-url}")
    private String apiCompletionUrl;

    @Value("${openai.api-url-chat}")
    private String apiChatUrl;
}