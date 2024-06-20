package project.domain.chatGpt.model.dto;

import lombok.*;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {
    String title;
    QuizLevel quizLevel;
    Integer count;
    QuizType quizType;
}
