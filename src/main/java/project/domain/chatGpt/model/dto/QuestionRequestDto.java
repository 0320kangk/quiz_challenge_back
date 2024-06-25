package project.domain.chatGpt.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {
    @NotNull
    String title;
    @NotNull
    QuizLevel quizLevel;
    @NotNull
    Integer count;
    @NotNull
    QuizType quizType;
}
