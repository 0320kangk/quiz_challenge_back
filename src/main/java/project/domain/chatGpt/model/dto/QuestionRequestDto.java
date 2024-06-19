package project.domain.chatGpt.model.dto;

import lombok.*;
import project.domain.chatGpt.model.enums.QuizLevel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {
    String title;
    QuizLevel quizLevel;
    Integer count;
}
