package project.domain.quizTheme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.quizTheme.service.QuizThemeService;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class QuizThemeController {
    private final QuizThemeService quizThemeService;
    @GetMapping("/api/quizTheme/all")
    public List<String> getAllQuizTheme() {
       return quizThemeService.getAllQuizTheme();
    }
}
