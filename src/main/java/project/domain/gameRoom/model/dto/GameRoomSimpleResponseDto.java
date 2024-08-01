package project.domain.gameRoom.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.domain.chatGpt.model.enums.QuizLevel;

@Getter
@Setter
@Builder
public class GameRoomSimpleResponseDto {
    private String name;
    private String theme;
    private Integer questionCount;
    QuizLevel quizLevel;
}
