package project.domain.gameRoom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.chatGpt.model.enums.QuizTitleEnum;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.GameRoomDto;
import project.domain.gameRoom.service.GameRoomService;

@RestController
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;

    @PostMapping("/gameRoom/create")
    public ResponseEntity<String> createGameRoom(GameRoomDto gameRoomDto) {
        try {
            gameRoomService.createGameRoom(gameRoomDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.internalServerError().build();
        }
    }


}
