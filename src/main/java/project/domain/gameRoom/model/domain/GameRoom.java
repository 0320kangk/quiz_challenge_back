package project.domain.gameRoom.model.domain;

import lombok.Builder;
import project.domain.chatGpt.model.enums.QuizTitleEnum;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
public class GameRoom {

    private Long id;
    private String name;
    private QuizTitleEnum title;

    private Integer questionCount;

    private GameRoomStatus status;

    private Set<String> participants;

    private String hostEmail;
}
