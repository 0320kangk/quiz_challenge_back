package project.domain.chat.chatRoom.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.domain.chat.chatRoom.model.dto.ChatRoomDto;

@Controller
public class ChatRoomController {


    @GetMapping("")
    public ResponseEntity<String> createChatRoom(ChatRoomDto chatRoomDto) {
        return null;
    }


}
