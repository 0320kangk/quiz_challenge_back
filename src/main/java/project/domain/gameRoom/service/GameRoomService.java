package project.domain.gameRoom.service;

import org.springframework.stereotype.Service;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.GameRoomIdDto;
import project.domain.gameRoom.model.dto.GameRoomRequestDto;
import project.domain.gameRoom.model.dto.GameRoomResponseDto;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameRoomService {
    //게임방을 어떻게 찾지?,
    //게임 방 번호와 나
    //key : room
    //key는 무엇? 방장의 id?.
    //
    private final Map<Long, GameRoom> gameRoomMap = new ConcurrentHashMap<>();
    private static final AtomicLong roomKey = new AtomicLong(0L);
    //room을 만들
    /*
    방 리스트에서는
    모든 방을 출력해줄꺼야,
    들어갔어
    메시지는 roomid와 작성자를 통해 보낼꺼야

    방번호 : 방
     */
    public GameRoomIdDto createGameRoom(GameRoomRequestDto gameRoomRequestDto, String email) {
        Set<String> set= Collections.synchronizedSet(new HashSet<>());
        GameRoom gameRoom = GameRoom.builder()
                .id(roomKey.incrementAndGet())
                .name(gameRoomRequestDto.getRoomName())
                .title(gameRoomRequestDto.getTitle())
                .questionCount(gameRoomRequestDto.getQuestionCount())
                .participants(set)
                .status(GameRoomStatus.WAITING)
                .hostEmail(null)
                .build();

        gameRoomMap.put(gameRoom.getId(), gameRoom);
        return new GameRoomIdDto (gameRoom.getId());

    }
    public List<GameRoomResponseDto> getGameRoomResponseDtos(){
        List<GameRoomResponseDto> gameRoomResponseDtos = gameRoomMap.values()
                .stream().map((gameRoom ->
                        GameRoomResponseDto.builder()
                                .id(gameRoom.getId())
                                .name(gameRoom.getName())
                                .title(gameRoom.getTitle().getValue())
                                .peopleCount(gameRoom.getParticipants().size())
                                .questionCount(gameRoom.getQuestionCount())
                                .status(gameRoom.getStatus())
                                .build()))
                .toList();
        return gameRoomResponseDtos;
    }
    public void enterGameRoom(Long roomId, String email){
        GameRoom gameRoom = gameRoomMap.get(roomId);
        if (gameRoom == null) {
            throw new IllegalArgumentException("해당 roomId에 해당하는 게임 방이 존재하지 않습니다.");
        }
        Set<String> participants =  gameRoom.getParticipants();
        if (participants.contains(email)) {
            throw new IllegalStateException("이미 방에 입장한 사용자입니다.");
        }

        if (participants.size() >= 4) {
            throw new IllegalStateException("방이 꽉 찼습니다.");
        }
        participants.add(email);
        if(participants.size() == 1) {
            gameRoom.setHostEmail(email);
        }
    }

}
