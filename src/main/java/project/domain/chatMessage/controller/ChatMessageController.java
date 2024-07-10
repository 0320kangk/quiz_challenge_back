package project.domain.chatMessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.domain.chatMessage.model.dto.ChatEnterMessageDto;
import project.domain.chatMessage.model.dto.ChatMessageDto;


@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate messageTemplate;
    @MessageMapping(value = "/chat/room/enter")
    public void enter(@Payload ChatEnterMessageDto enterMessage) {
        ChatMessageDto message = ChatMessageDto.builder()
                .roomId(enterMessage.getRoomId())
                .writer(enterMessage.getWriter())
                .message(enterMessage.getWriter() + "님이 채팅방에 참여하였습니다.")
                .build();
        messageTemplate.convertAndSend("/subscribe/chat/room/" + message.getRoomId(), message);
    }
    @MessageMapping(value = "/chat/room/message")
    public void message(@Payload ChatMessageDto message ) {
        messageTemplate.convertAndSend("/subscribe/chat/room/" + message.getRoomId() , message);
    }
}
