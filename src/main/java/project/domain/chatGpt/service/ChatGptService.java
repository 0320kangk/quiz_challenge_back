package project.domain.chatGpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.domain.chatGpt.config.ChatGptProperties;
import project.domain.chatGpt.dto.ChatCompletionRequestDto;
import project.domain.chatGpt.dto.CompletionRequestDto;
import project.domain.chatGpt.dto.Messages;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {
    private final ChatGptProperties chatGptProperties;

    private final RestTemplate restTemplate;
    public String getCompletion(String prompt) {
        String apiUrl = chatGptProperties.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + chatGptProperties.getApiKey());

        CompletionRequestDto completionRequestDto = CompletionRequestDto.builder()
                .prompt(prompt)
                .model("gpt-3.5-turbo-instruct")
                .max_tokens(3200)
                .temperature(0)
                .build();
        log.info("CHAT_GPT_API_KEY {}" ,  chatGptProperties.getApiKey());
        HttpEntity<CompletionRequestDto> entity = new HttpEntity<>(completionRequestDto, headers);

        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class).getBody();
    }

    public String getChatCompletion(String prompt) {
        String apiUrl = chatGptProperties.getApiUrlChat();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + chatGptProperties.getApiKey());

//        Messages  m1 = Messages
//                .builder()
//                .role("system")
//                .content("이 질문은 퀴즈에 관한 문제야")
//                .build();
        Messages m2 =Messages
                .builder()
                .role("user")
                .content(prompt)
                .build();
        Messages[] messages = new Messages[1];
//        messages[0] = m1;
        messages[0] = m2;
        //chat 형은 gpt-3.5-turbo
        //prompt 는gpt-3.5-turbo-instruct
        ChatCompletionRequestDto completionRequestDto = ChatCompletionRequestDto.builder()
                .model("gpt-3.5-turbo")
                .max_tokens(3200)
                .temperature(0)
                .messages(messages)
                .build();
        log.info("CHAT_GPT_API_KEY {}" , chatGptProperties.getApiKey());
        HttpEntity<ChatCompletionRequestDto> entity = new HttpEntity<>(completionRequestDto, headers);

        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class).getBody();
    }
}
