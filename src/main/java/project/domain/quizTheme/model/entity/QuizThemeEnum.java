package project.domain.quizTheme.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizThemeEnum {
    SPRINGFRAMEWORK("Spring framework"),
    JAVA("Java"),
    PYTHON("Python"),
    BASICMATH("Basic Math"),
    WORLDHISTORY("World History"),
    ENGLISHGRAMMAR("English Grammar"),
    KOREAGRAMMAR("Korea Grammar");
    private final String value;
}