package project.domain.chatGpt.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.chatGpt.service.ChatGptService;

@RestController
@RequestMapping("/api/chatGpt")
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @PostMapping("/completion")
    public String getCompletion(@RequestBody String prompt) {
        return chatGptService.getChatCompletion(prompt);
    }
}
