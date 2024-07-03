package project.domain.gameRoom.model.domain;

import lombok.Builder;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class GameRoom {

    private Long id;

    private String name;

    private Integer questionCount;

    private GameRoomStatus status;

    private String hostEmail;
}
