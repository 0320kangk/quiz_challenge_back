package project.domain.gameRoom.model.dto;

import lombok.Getter;
import lombok.Setter;
import project.domain.chatGpt.model.enums.QuizTitleEnum;

@Getter
@Setter
public class GameRoomDto {
    private String emailId;
    private String roomName;
    private Integer questionCount;
    private QuizTitleEnum title;
}
