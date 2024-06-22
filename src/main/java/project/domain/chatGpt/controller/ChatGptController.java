package project.domain.chatGpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.ChatContentDto;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.service.ChatGptChatCompletionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/chatGpt")
@RequiredArgsConstructor
public class ChatGptController {

//    private final ChatGptCompletionServiceImpl chatGptServiceImpl;
    private final ChatGptChatCompletionServiceImpl chatGptChatCompletionServiceImpl;


    @PostMapping("/chat/completion")
    public ChatCompletionResponseDto getChatCompletion(@RequestBody QuestionRequestDto questionRequestDto) throws JsonProcessingException {
        return chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
    }
    @PostMapping("/chat/completion/content")
    public ChatContentDto getChatCompletionContent(@RequestBody QuestionRequestDto questionRequestDto) throws JsonProcessingException {
        ChatCompletionResponseDto chatCompletion = chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
        List<ChatCompletionResponseDto.Choice> choices = chatCompletion.getChoices();
        if(!choices.isEmpty()) return new ChatContentDto(choices.get(0).getMessage().getContent());
        else throw new IllegalArgumentException("List is null or empty");
    }
}
