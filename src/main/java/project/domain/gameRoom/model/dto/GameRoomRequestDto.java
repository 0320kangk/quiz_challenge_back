package project.domain.gameRoom.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.domain.chatGpt.model.enums.QuizTitleEnum;

@Getter
@Setter
@ToString
public class GameRoomRequestDto {
    private String email;
    private String roomName;
    private Integer questionCount;
    private QuizTitleEnum title;
}
