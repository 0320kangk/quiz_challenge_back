package project.domain.gameRoom.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.quizTheme.model.entity.QuizThemeEnum;

@Getter
@Setter
@ToString
public class GameRoomRequestDto {
    @NotNull
    private String roomName;
    @NotNull
    private Integer questionCount;
    @NotNull
    private QuizThemeEnum theme;
    @NotNull
    QuizLevel quizLevel;
}
