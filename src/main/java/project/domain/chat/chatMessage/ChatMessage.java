package project.domain.chat.chatMessage;

import project.domain.chat.chatRoom.ChatRoom;
import project.domain.member.entity.Member;
import jakarta.persistence.*;
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
