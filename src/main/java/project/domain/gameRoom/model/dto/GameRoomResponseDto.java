package project.domain.gameRoom.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

@Getter
@Setter
@Builder
public class GameRoomResponseDto {
    private Long id;

    private String name;

    private Integer questionCount;

    private Integer people;

    private GameRoomStatus status;


}
