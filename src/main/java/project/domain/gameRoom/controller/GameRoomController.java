package project.domain.gameRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.domain.gameRoom.model.dto.GameRoomIdDto;
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
    public ResponseEntity<?> createGameRoom(@RequestBody GameRoomRequestDto gameRoomRequestDto,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("gameRoom create request: {}", gameRoomRequestDto);
        try {
            GameRoomIdDto gameRoom = gameRoomService.createGameRoom(gameRoomRequestDto, userDetails.getUsername());
            return ResponseEntity.ok().body(gameRoom);
        } catch (IllegalArgumentException e){
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @GetMapping("/api/gameRoom/all")
    public List<GameRoomResponseDto> getGameRooms(){
        return gameRoomService.getGameRoomResponseDtos();
    }
    @PostMapping("/api/gameRoom/enter/{roomId}")
    public ResponseEntity<String> enterGameRoom(@PathVariable("roomId") Long roomId,@RequestBody String email) {
        log.info("enterGameRoom 호출 roomId {}", roomId);
        try {
            gameRoomService.enterGameRoom(roomId, email);
            return ResponseEntity.ok().body("방 입장 성공.");
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    //호스트가 방 나가면 방 삭제
}
