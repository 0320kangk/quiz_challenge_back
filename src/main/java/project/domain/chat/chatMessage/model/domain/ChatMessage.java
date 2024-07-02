package project.domain.chat.chatMessage.model.domain;

import project.domain.chat.chatRoom.model.domain.ChatRoom;
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

    ChatRoom chatRoom;

}
