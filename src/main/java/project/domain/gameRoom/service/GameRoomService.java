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
    //key : uuid, value: gameRoom
    private final Map<String, GameRoom> gameRoomMap = new ConcurrentHashMap<>();
    //key: sessionId, value:
    private final Map<String, String> sessionRoomMap = new ConcurrentHashMap<>();
    private final Integer maxRoomPeople = 4;
    public GameRoomIdDto createGameRoom(GameRoomRequestDto gameRoomRequestDto) {
        String roomId = UUID.randomUUID().toString();
        Set<String> set= Collections.synchronizedSet(new HashSet<>());
        GameRoom gameRoom = GameRoom.builder()
                .id(roomId)
                .name(gameRoomRequestDto.getRoomName())
                .title(gameRoomRequestDto.getTitle())
                .questionCount(gameRoomRequestDto.getQuestionCount())
                .participants(set)
                .status(GameRoomStatus.WAITING)
                .hostId(null)
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
    public void addGameRoom(String roomId, String sessionId){
        GameRoom gameRoom = gameRoomMap.get(roomId);
        if (gameRoom == null) {
            throw new IllegalArgumentException("해당 roomId에 해당하는 게임 방이 존재하지 않습니다.");
        }
        Set<String> participants = gameRoom.getParticipants();
        if (participants.contains(sessionId)) {
            throw new IllegalStateException("이미 방에 입장한 사용자입니다.");
        }

        if (participants.size() >= maxRoomPeople) {
            throw new IllegalStateException("방이 꽉 찼습니다.");
        }
        participants.add(sessionId);
        setRoomId (sessionId, roomId);
        if(participants.size() == 1) {
            gameRoom.setHostId(sessionId);
        }
    }
    public void leaveGameRoom(String roomId, String sessionId){
        if(gameRoomMap.containsKey(roomId)){
            GameRoom gameRoom = gameRoomMap.get(roomId);
            Set<String> participants = gameRoom.getParticipants();
            boolean remove = participants.remove(sessionId);
            if(remove && gameRoom.getHostId().equals(sessionId)&& !participants.isEmpty()){
                gameRoom.setHostId(participants.stream().findFirst().get()); //방장 변경
            }
            if(!remove){
                throw new NoSuchElementException("방에 존재하지 않는 회원입니다.");
            }
            if(participants.isEmpty()){
                gameRoomMap.remove(roomId); // 사람이 없다면 방 삭제
                sessionRoomMap.remove(sessionId);
            }
        }
    }

    public String getRoomId(String sessionId) {
        return sessionRoomMap.get(sessionId);
    }
    public void setRoomId(String sessionId, String roomId) {
        sessionRoomMap.put(sessionId,roomId);
    }

}
