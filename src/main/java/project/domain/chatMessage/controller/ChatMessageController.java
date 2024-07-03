package project.domain.chatMessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.domain.chatMessage.model.dto.ChatMessageDto;


@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate messageTemplate;

    @MessageMapping(value = "/chat/enter")
    public void enter(@Payload ChatMessageDto message) {
        message.setMessage(message.getWriter() +  "님이 채팅방에 참여하였습니다.");
        messageTemplate.convertAndSend("/subscribe/chat/room/" + message.getRoomId(), message);

    }
    @MessageMapping(value = "/chat/message")
    public void message( ChatMessageDto message ) {
        messageTemplate.convertAndSend("/publish/chat/room/" + message.getRoomId() , message);
    }
}
