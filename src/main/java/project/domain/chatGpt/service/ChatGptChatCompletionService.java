package project.domain.chatGpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ChatGptChatCompletionService {
    public String getChatCompletion(String prompt) throws JsonProcessingException;

}
