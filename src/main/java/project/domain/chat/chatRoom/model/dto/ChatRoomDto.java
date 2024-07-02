package project.domain.chat.chatRoom.model.dto;

import lombok.Getter;
import lombok.Setter;
import project.domain.chat.chatRoom.enumerate.ChatRoomStatus;
import project.domain.member.entity.Member;

@Getter
@Setter
public class ChatRoomDto {

    private Long memberId;
    private String roomName;
    private Integer questionCount;
    private String topic;

}
