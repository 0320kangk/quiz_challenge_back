package project.domain.chatMessage.model.domain;

import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ChatMessage {

    Long id;

    String message;

    LocalDateTime createdDate;

    Member sender;

    GameRoom gameRoom;

}
