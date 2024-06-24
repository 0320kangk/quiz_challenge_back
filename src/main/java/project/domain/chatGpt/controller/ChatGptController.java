package project.domain.chatGpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.ChatContent;
import project.domain.chatGpt.model.dto.ChatContentDto;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.service.ChatGptChatCompletionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/chatGpt")
@RequiredArgsConstructor
@Slf4j
public class ChatGptController {

//    private final ChatGptCompletionServiceImpl chatGptServiceImpl;
    private final ChatGptChatCompletionServiceImpl chatGptChatCompletionServiceImpl;


    @PostMapping("/chat/completion")
    public ChatCompletionResponseDto getChatCompletion(@RequestBody QuestionRequestDto questionRequestDto) throws JsonProcessingException {
        return chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
    }
    @PostMapping("/chat/completion/content")
    public List<ChatContent> getChatCompletionContent(@RequestBody QuestionRequestDto questionRequestDto) throws JsonProcessingException {
        ChatCompletionResponseDto chatCompletion = chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);

        List<ChatContent> chatContent = chatGptChatCompletionServiceImpl.getChatContent(chatCompletion);
        log.info("chatContents : {} ", chatContent);
        return chatContent;
    }
}
