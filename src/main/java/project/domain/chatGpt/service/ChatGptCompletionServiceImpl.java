package project.domain.chatGpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ChatGptCompletionServiceImpl implements ChatGptCompletionService {
    private final ChatGptProperties chatGptProperties;

    private final RestTemplate restTemplate;

    @Override
    public String getCompletion(String prompt) {
        String apiUrl = chatGptProperties.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + chatGptProperties.getApiKey());

        // 문제 질문이 오면
        // 어떻게 응답해야할까
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


}
