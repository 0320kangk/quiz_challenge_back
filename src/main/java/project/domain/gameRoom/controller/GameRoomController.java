package project.domain.gameRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.domain.gameRoom.model.dto.GameRoomRequestDto;
import project.domain.gameRoom.model.dto.GameRoomResponseDto;
import project.domain.gameRoom.service.GameRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameRoomController {
    private final GameRoomService gameRoomService;
    @PostMapping("/api/gameRoom/create")
    public ResponseEntity<String> createGameRoom(@RequestBody GameRoomRequestDto gameRoomRequestDto) {
        log.info("gameRoom create request: {}", gameRoomRequestDto);
        try {
            gameRoomService.createGameRoom(gameRoomRequestDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/api/gameRoom/create")
    public List<GameRoomResponseDto> getGameRooms(){
        return gameRoomService.getGameRoomResponseDtos();
    }

    @PostMapping("/api/gameRoom/enter/{roomId}")
    public ResponseEntity<String> enterGameRoom(@PathVariable("roomId") Long roomId) {
        log.info("enterGameRoom 호출 roomId {}", roomId);
        try {
            gameRoomService.enterGameRoom(roomId);
            return ResponseEntity.ok().body("방에 입장합니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().body("방 인원수가 꽉차있습니다.");
        }
    }
    //호스트가 방 나가면 방 삭제
}
