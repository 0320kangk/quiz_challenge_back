package project.domain.chatMessage.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomUserScoreDto {
    String roomId;
    String userName;
    Integer score;
}
