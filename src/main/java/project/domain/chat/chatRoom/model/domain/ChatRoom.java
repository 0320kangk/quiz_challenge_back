package project.domain.chat.chatRoom.model.domain;

import project.domain.chat.chatRoom.enumerate.ChatRoomStatus;
import project.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ChatRoom {

    Long id;

    private String name;

    private Integer questionCount;

    private ChatRoomStatus status;

    private Member host;

}
