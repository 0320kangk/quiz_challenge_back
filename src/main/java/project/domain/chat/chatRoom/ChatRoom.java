package project.domain.chat.chatRoom;

import project.domain.chat.chatMessage.ChatMessage;
import project.domain.chat.chatRoom.enumerate.ChatRoomStatus;
import project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ChatRoom {

    Long id;


    private Integer problemCount;

    private ChatRoomStatus status;

    private LocalDateTime createdDate;

    private Member host;
    
}
