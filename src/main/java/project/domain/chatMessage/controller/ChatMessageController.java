package project.domain.chatMessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.domain.chatMessage.model.dto.ChatEnterMessageDto;
import project.domain.chatMessage.model.dto.ChatMessageDto;
import project.domain.gameRoom.model.dto.GameRoomHostIdDto;
import project.domain.gameRoom.service.GameRoomService;


@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate messageTemplate;
    private final GameRoomService gameRoomService;
    @MessageMapping(value = "/chat/room/enter")
    public void enter(@Payload ChatEnterMessageDto enterMessage) {
        ChatMessageDto message = ChatMessageDto.builder()
                .roomId(enterMessage.getRoomId())
                .writer(enterMessage.getWriter())
                .message(enterMessage.getWriter() + "님이 채팅방에 참여하였습니다.")
                .build();
        messageTemplate.convertAndSend("/subscribe/chat/room/" + message.getRoomId(), message);
        GameRoomHostIdDto gameRoomHostIdDto = new GameRoomHostIdDto(gameRoomService.getHostId(message.getRoomId()));
        messageTemplate.convertAndSend("/subscribe/notification/room/" + message.getRoomId() ,gameRoomHostIdDto);

    }
    @MessageMapping(value = "/chat/room/message")
    public void message(@Payload ChatMessageDto message ) {
        messageTemplate.convertAndSend("/subscribe/chat/room/" + message.getRoomId() , message);
    }


}
