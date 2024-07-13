package project.domain.gameRoom.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.GameRoomIdDto;
import project.domain.gameRoom.model.dto.GameRoomRequestDto;
import project.domain.gameRoom.model.dto.GameRoomResponseDto;
import project.domain.gameRoom.model.dto.GameRoomSimpleResponseDto;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;
import project.domain.security.jwt.util.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
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
                .quizLevel(gameRoomRequestDto.getQuizLevel())
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
    public GameRoom addGameRoom(String roomId, String sessionId){
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
            String hostId = SecurityUtil.getCurrentUsername().orElseThrow(() -> new NoSuchElementException("회원 id를 찾을 수 없습니다."));
            log.info("host ID {} ",hostId);
            gameRoom.setHostId(hostId);
        }
        return gameRoom;
    }
    public GameRoom leaveGameRoom(String roomId, String sessionId){
        if(gameRoomMap.containsKey(roomId)){
            GameRoom gameRoom = gameRoomMap.get(roomId);
            Set<String> participants = gameRoom.getParticipants();
            boolean remove = participants.remove(sessionId);
            String userName = SecurityUtil.getCurrentUsername().orElseThrow(() -> new NoSuchElementException("회원 id를 찾을 수 없습니다."));
            if(remove && gameRoom.getHostId().equals(userName)&& !participants.isEmpty()){
                gameRoom.setHostId(participants.stream().findFirst().get()); //방장 변경
            }
            if(!remove){
                throw new NoSuchElementException("방에 존재하지 않는 회원입니다.");
            }
            if(participants.isEmpty()){
                gameRoomMap.remove(roomId); // 사람이 없다면 방 삭제
                sessionRoomMap.remove(sessionId);
            }
            return gameRoom;
        }
        throw new IllegalArgumentException("존재하지 않는 roomId 입니다.");
    }

    public String getRoomId(String sessionId) {
        return sessionRoomMap.get(sessionId);
    }
    public void setRoomId(String sessionId, String roomId) {
        sessionRoomMap.put(sessionId,roomId);
    }
    public GameRoomSimpleResponseDto getGameRoomSimpleResponseDto (String roomId) {
        GameRoom gameRoom = gameRoomMap.get(roomId);
        return GameRoomSimpleResponseDto.builder()
                .title(gameRoom.getTitle())
                .name(gameRoom.getName())
                .questionCount(gameRoom.getQuestionCount())
                .quizLevel(gameRoom.getQuizLevel())
                .build();
    }
    public QuestionRequestDto getQuestionRequestDto (String roomId) {
        log.info("gameRoomMap {}", gameRoomMap);
        GameRoom gameRoom = gameRoomMap.get(roomId);
        log.info("gameRoom test: {}", gameRoom);
        return QuestionRequestDto.builder()
                .title(gameRoom.getTitle().getValue())
                .count(gameRoom.getQuestionCount())
                .quizLevel(gameRoom.getQuizLevel())
                .build();
    }
}
