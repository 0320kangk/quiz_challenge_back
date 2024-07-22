package project.domain.gameRoom.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizThemeEnum;

@Getter
@Setter
@Builder
public class GameRoomSimpleResponseDto {
    private String name;
    private QuizThemeEnum theme;
    private Integer questionCount;
    QuizLevel quizLevel;
}
