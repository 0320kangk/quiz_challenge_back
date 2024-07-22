package project.domain.quizTheme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.domain.quizTheme.repository.QuizThemeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizThemeService {
    private final QuizThemeRepository quizThemeRepository;

    public List<String> getAllQuizTheme() {
        List<String> allThemes = quizThemeRepository.findAllThemes();
        log.info("allThemes : {}", allThemes);
        return allThemes;
    }
}
