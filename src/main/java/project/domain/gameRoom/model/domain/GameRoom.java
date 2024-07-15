package project.domain.gameRoom.model.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.ToString;
import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizTitleEnum;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@Builder
public class GameRoom {

    private String id;
    private String name;
    private QuizTitleEnum title;
    private Integer questionCount;
    private GameRoomStatus status;
    private Set<String> participants;
    private String hostName;
    private QuizLevel quizLevel;
}
