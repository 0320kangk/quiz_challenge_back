package project.domain.gameRoom.model.domain;

import lombok.Builder;
import lombok.ToString;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizThemeEnum;
import project.domain.gameRoom.model.dto.GameRoomParticipant;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@ToString
@Builder
public class GameRoom {

    private String id;
    private String name;
    private QuizThemeEnum theme;
    private Integer questionCount;
    private GameRoomStatus status;
    private Set<GameRoomParticipant> participants;
    private String hostName;
    private QuizLevel quizLevel;
}
