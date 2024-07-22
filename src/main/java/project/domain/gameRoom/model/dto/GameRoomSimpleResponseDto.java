package project.domain.gameRoom.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizTitleEnum;

@Getter
@Setter
@Builder
public class GameRoomSimpleResponseDto {
    private String name;
    private QuizTitleEnum title;
    private Integer questionCount;
    QuizLevel quizLevel;
}
