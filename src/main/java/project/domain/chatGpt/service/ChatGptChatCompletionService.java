package project.domain.chatGpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.model.entity.QuizQuestion;

public interface ChatGptChatCompletionService {
    public String getChatCompletion(QuestionRequestDto questionRequestDto) throws JsonProcessingException;

}
