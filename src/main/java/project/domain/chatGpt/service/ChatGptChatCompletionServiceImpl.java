package project.domain.chatGpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import project.domain.chatGpt.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.dto.Messages;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptChatCompletionServiceImpl implements ChatGptChatCompletionService {

    private final ChatGptProperties chatGptProperties;

    private final RestTemplate restTemplate;
    @Override
    public String getChatCompletion(String prompt) throws JsonProcessingException {
        String apiUrl = chatGptProperties.getApiUrlChat();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + chatGptProperties.getApiKey());

        Messages  m1 = Messages
                .builder()
                .role("system")
                .content("""
                        You need to create a quiz question. Answer all questions in Korean. Please create 10 5-choice quiz questions about the Spring Framework. I would like each question to be provided in JSON format. The JSON structure should look like this: {
                          "question": "Question content",
                          "options": [
                            "Choice 1",
                            "Choice 2",
                            "Choice 3",
                            "Choice 4",
                            "Choice 5"
                          ],
                          "answer": "Correct answer option number (0-4)"
                        } Example: {
                          "question": "Spring Framework의 핵심 기능은 무엇인가요?",
                          "options": [
                            "의존성 주입",
                            "관점 지향 프로그래밍",
                            "스프링 부트",
                            "스프링 클라우드",
                            "모듈화"
                          ],
                          "answer": 0
                        }
                        also { \\"response_format\\": { \\"type\\": \\"json_object\\" }
                        """)
                .build();
        Messages m2 =Messages
                .builder()
                .role("user")
                .content(prompt)
                .build();
        Messages[] messages = new Messages[2];
        messages[0] = m1;
        messages[1] = m2;
        //chat 형은 gpt-3.5-turbo
        //prompt 는gpt-3.5-turbo-instruct
        ChatCompletionRequestDto completionRequestDto = ChatCompletionRequestDto.builder()
                .model("gpt-3.5-turbo")
                .max_tokens(3200)
                .temperature(1.0f)
                .messages(messages)
                .build();
        log.info("CHAT_GPT_API_KEY {}" , chatGptProperties.getApiKey());
        HttpEntity<ChatCompletionRequestDto> entity = new HttpEntity<>(completionRequestDto, headers);
        ObjectMapper mapper = new ObjectMapper();

        String body = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class).getBody();
        ChatCompletionResponseDto response = mapper.readValue(body, ChatCompletionResponseDto.class);
        log.info("json data response : {}", response);
        return body;
    }
}
