package project.domain.gameRoom.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

@Getter
@Setter
@Builder
public class GameRoomResponseDto {
    private String id;

    private String name;

    private String title;

    private Integer questionCount;

    private Integer peopleCount;

    private GameRoomStatus status;


}
