package project.domain.chatMessage.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomStatusDto {
    boolean loading;
    boolean gameStarted;
    boolean gameEnded;
}
