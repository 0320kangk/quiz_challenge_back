package project.domain.chatGpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.ChatContent;
import project.domain.chatGpt.model.dto.QuestionRequestDto;

import java.util.List;

public interface ChatGptChatCompletionService {
    public ChatCompletionResponseDto getChatCompletion(QuestionRequestDto questionRequestDto) throws JsonProcessingException;

    public List<ChatContent> getChatContent(ChatCompletionResponseDto chatCompletionResponseDto) throws JsonProcessingException;


}