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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.domain.chatGpt.config.ChatGptProperties;
import project.domain.chatGpt.model.dto.ChatCompletionRequestDto;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.Messages;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.model.entity.QuizQuestion;
import project.domain.chatGpt.model.entity.QuizTitle;
import project.domain.chatGpt.repository.QuizQuestionsRepository;
import project.domain.chatGpt.repository.QuizTitleRepository;
import project.domain.chatGpt.util.ChatGptUtil;
import project.domain.chatGpt.util.QuestionsFormatting;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptChatCompletionServiceImpl implements ChatGptChatCompletionService {

    private final ChatGptProperties chatGptProperties;

    private final RestTemplate restTemplate;
    private final QuizQuestionsRepository quizQuestionsRepository;
    private final QuizTitleRepository quizTitleRepository;
    @Override
    @Transactional
    public String getChatCompletion(QuestionRequestDto questionRequestDto) throws JsonProcessingException {
        String apiUrl = chatGptProperties.getApiChatUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + chatGptProperties.getApiKey());

        String systemContent = ChatGptUtil.createSystemContent(ChatGptUtil.createSystemQuestion(questionRequestDto));
        log.info("questionRequestDto.getTitle() {}",questionRequestDto.getTitle());
        QuizTitle quizTitle = quizTitleRepository.findByTitle(questionRequestDto.getTitle()).orElseThrow(() -> new IllegalArgumentException("잘못된 매개변수입니다."));
        List<QuizQuestion> byQuizTitle = quizQuestionsRepository.findByQuizTitle(quizTitle);

        Random random = new Random();
        QuizQuestion quizQuestion = byQuizTitle.get(random.nextInt(byQuizTitle.size()));
        String userContent = ChatGptUtil.createUserContent(questionRequestDto.getCount(), quizQuestion.getTopic());

        Messages  systemMessage = Messages
                .builder()
                .role("system")
                .content(systemContent)
                .build();
        Messages userMessage = Messages
                .builder()
                .role("user")
                .content(userContent)
                .build();
        Messages[] messages = new Messages[2];
        messages[0] = systemMessage;
        messages[1] = userMessage;
        ChatCompletionRequestDto completionRequestDto = ChatCompletionRequestDto.builder()
                .model("gpt-3.5-turbo")
                .max_tokens(3200)
                .temperature(1.0f)
                .messages(messages)
                .build();
        log.info("CHAT_GPT_API_KEY {}" , chatGptProperties.getApiKey());
        HttpEntity<ChatCompletionRequestDto> entity = new HttpEntity<>(completionRequestDto, headers);
        ObjectMapper mapper = new ObjectMapper();

        //다양한 주제를 이용하여 만들어야 함
        log.info("system content : {}", systemContent);
        log.info("user content : {}", quizQuestion.getTopic());

        String body = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class).getBody();
        ChatCompletionResponseDto response = mapper.readValue(body, ChatCompletionResponseDto.class);
        log.info("json data response : {}", response);
        return body;
    }
}
