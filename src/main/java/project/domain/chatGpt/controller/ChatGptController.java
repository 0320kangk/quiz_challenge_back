package project.domain.chatGpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.ChatContent;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.service.ChatGptChatCompletionServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chatGpt")
@RequiredArgsConstructor
@Slf4j
public class ChatGptController {

//    private final ChatGptCompletionServiceImpl chatGptServiceImpl;
    private final ChatGptChatCompletionServiceImpl chatGptChatCompletionServiceImpl;
    private static final int MAX_RETRIES = 3; // 최대 재요청 횟수

    @PostMapping("/chat/completion")
    public ChatCompletionResponseDto getChatCompletion(@RequestBody QuestionRequestDto questionRequestDto) throws JsonProcessingException {
        return chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
    }
    @PostMapping("/chat/completion/content")
    public ResponseEntity<?> getChatCompletionContent(@RequestBody @Validated QuestionRequestDto questionRequestDto)  {
        int retries = 0;
        List<ChatContent> chatContent = new ArrayList<>();
        while( chatContent.size() != questionRequestDto.getCount() && retries < MAX_RETRIES  ){
            try {
                ChatCompletionResponseDto chatCompletion = chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
                chatContent = chatGptChatCompletionServiceImpl.getChatContent(chatCompletion);
            }catch (JsonProcessingException e){
                log.error("JSON 처리 중 오류 발생: {}", e.getMessage());
                retries++;
                continue; // 예외가 발생하면 재시도
            }
            retries++;
        }
        log.info("chatContents : {} ", chatContent);
        if(chatContent.size() != questionRequestDto.getCount() ) {
            String errorMessage = String.format("문제를 생성을 위해 %d 시도 했지만 실패했습니다.", MAX_RETRIES);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
        return ResponseEntity.ok(chatContent);
    }
}
