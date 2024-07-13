package project.domain.gameRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.GameRoomIdDto;
import project.domain.gameRoom.model.dto.GameRoomRequestDto;
import project.domain.gameRoom.model.dto.GameRoomResponseDto;
import project.domain.gameRoom.model.dto.GameRoomSimpleResponseDto;
import project.domain.gameRoom.service.GameRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameRoomController {
    private final GameRoomService gameRoomService;
    @PostMapping("/api/gameRoom/create")
    public ResponseEntity<?> createGameRoom(@RequestBody GameRoomRequestDto gameRoomRequestDto) {
        log.info("gameRoom create request: {}", gameRoomRequestDto);
        try {
            GameRoomIdDto gameRoom = gameRoomService.createGameRoom(gameRoomRequestDto);
            return ResponseEntity.ok().body(gameRoom);
        } catch (IllegalArgumentException e){
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @GetMapping("/api/gameRoom/all")
    public List<GameRoomResponseDto> getGameRooms(){
        gameRoomService.cleanGameRoom();
        return gameRoomService.getGameRoomResponseDtos();
    }
    @GetMapping("/api/gameRoom/{roomId}")
    public ResponseEntity<GameRoomSimpleResponseDto> getGameRoom(@PathVariable("roomId") String roomId) {
        return ResponseEntity.ok().body(gameRoomService.getGameRoomSimpleResponseDto(roomId));
    }

    //그냥 id만 통해서 한번에 찾고 문제 만들면 되는거 아님?
    //


}
