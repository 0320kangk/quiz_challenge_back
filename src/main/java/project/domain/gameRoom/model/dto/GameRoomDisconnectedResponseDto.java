package project.domain.gameRoom.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GameRoomDisconnectedResponseDto {
    String hostName;
    String leftPerson;
    String content;
}
