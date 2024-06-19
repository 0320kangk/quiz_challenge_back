package project.domain.chatGpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.service.ChatGptChatCompletionServiceImpl;
import project.domain.chatGpt.service.ChatGptCompletionServiceImpl;

@RestController
@RequestMapping("/api/chatGpt")
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptCompletionServiceImpl chatGptServiceImpl;
    private final ChatGptChatCompletionServiceImpl chatGptChatCompletionServiceImpl;


    @PostMapping("/chat/completion")
    public String getCompletion(@RequestBody QuestionRequestDto questionRequestDto) throws JsonProcessingException {

        return chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
    }
}
