package project.domain.gameRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.model.enums.QuizType;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.*;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;
import project.domain.member.entity.Member;
import project.domain.member.repository.MemberRepository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameRoomService {
    //key : uuid, value: gameRoom
    private final Map<String, GameRoom> gameRoomMap = new ConcurrentHashMap<>();
    //key: sessionId, value: roomid
    private final Map<String, String> nameToRoomMap = new ConcurrentHashMap<>();
    private final Map<String, String> idToNameMap = new ConcurrentHashMap<>();//email:name
    private final Integer maxRoomPeople = 4;

    private final MemberRepository memberRepository;


    public GameRoomIdDto createGameRoom(GameRoomRequestDto gameRoomRequestDto) {
        String roomId = UUID.randomUUID().toString();
        Set<GameRoomParticipant> set= Collections.synchronizedSet(new HashSet<>());
        GameRoom gameRoom = GameRoom.builder()
                .id(roomId)
                .name(gameRoomRequestDto.getRoomName())
                .theme(gameRoomRequestDto.getTheme())
                .questionCount(gameRoomRequestDto.getQuestionCount())
                .participants(set)
                .status(GameRoomStatus.WAITING)
                .quizLevel(gameRoomRequestDto.getQuizLevel())
                .hostName(null)
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
                                .theme(gameRoom.getTheme().getValue())
                                .peopleCount(gameRoom.getParticipants().size())
                                .questionCount(gameRoom.getQuestionCount())
                                .status(gameRoom.getStatus())
                                .build()))
                                .toList();
        return gameRoomResponseDtos;
    }
    //방 인원수가 없다면 방 제거
    public void cleanGameRoom() {
        log.info(("gameRoomMap {}"), gameRoomMap.size());
        for (Map.Entry<String, GameRoom> stringGameRoomEntry : gameRoomMap.entrySet()) {
            if(stringGameRoomEntry.getValue().getParticipants().isEmpty()){
                gameRoomMap.remove(stringGameRoomEntry.getKey());
            }
        }
    }
    public GameRoom enterGameRoom(String roomId,  String participantId){
        GameRoom gameRoom = gameRoomMap.get(roomId);
        Member member = memberRepository.findOneWithCharacterImgByEmail(participantId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 email 입니다."));
        String name = member.getName();
        log.info("name {}", name);
        if (gameRoom == null) {
            throw new IllegalArgumentException("해당 roomId에 해당하는 게임 방이 존재하지 않습니다.");
        }
        Set<GameRoomParticipant> participants = gameRoom.getParticipants();
        if (participants.contains(name)) {
            throw new IllegalStateException("이미 방에 입장한 사용자입니다.");
        }
        if (participants.size() >= maxRoomPeople) {
            throw new IllegalStateException("방이 꽉 찼습니다.");
        }
        participants.add(GameRoomParticipant.builder()
                .name(name)
                .characterName(member.getCharacterImg().getImgName())
                .build());
        nameToRoomMap.put(name, roomId);
        idToNameMap.put(participantId, name);
        log.info("participants size {}", participants.size());
        if(participants.size() == 1) {
            log.info("host ID {} ",name);
            gameRoom.setHostName(name);
        }
        return gameRoom;
    }
    public GameRoom leaveGameRoom(String roomId, String id){
        if(gameRoomMap.containsKey(roomId)){
            GameRoom gameRoom = gameRoomMap.get(roomId);
            Set<GameRoomParticipant> participants = gameRoom.getParticipants();
            String name = idToNameMap.get(id);
            boolean leaveParticipant = participants.removeIf(participant -> name.equals(participant.getName()));
            if(leaveParticipant && gameRoom.getHostName().equals(name) && !participants.isEmpty()){
                gameRoom.setHostName(participants.stream().findFirst().get().getName()); //방장 변경
            }
            if(!leaveParticipant){
                throw new NoSuchElementException("방에 존재하지 않는 회원입니다.");
            }
            if(participants.isEmpty()){
                gameRoomMap.remove(roomId); // 사람이 없다면 방 삭제
                nameToRoomMap.remove(name);
            }
            return gameRoom;
        }
        throw new IllegalArgumentException("존재하지 않는 roomId 입니다.");
    }
    public String getHostName(String roomId){
        if(gameRoomMap.get(roomId) != null) {
            log.info("host name {}", gameRoomMap.get(roomId).getHostName());
            return gameRoomMap.get(roomId).getHostName();
        }
        throw new IllegalArgumentException("잘못된 roomId 입니다.");
    }
    public String getIdToRoomId(String id) {
        return nameToRoomMap.get(idToNameMap.get(id));
    }

    public GameRoomSimpleResponseDto getGameRoomSimpleResponseDto (String roomId) {
        GameRoom gameRoom = gameRoomMap.get(roomId);
        return GameRoomSimpleResponseDto.builder()
                .theme(gameRoom.getTheme().getValue())
                .name(gameRoom.getName())
                .questionCount(gameRoom.getQuestionCount())
                .quizLevel(gameRoom.getQuizLevel())
                .build();
    }
    public QuestionRequestDto getQuestionRequestDto (String roomId, QuizType quizType) {
        log.info("gameRoomMap {}", gameRoomMap);
        GameRoom gameRoom = gameRoomMap.get(roomId);
        log.info("gameRoom test: {}", gameRoom);
        return QuestionRequestDto.builder()
                .theme(gameRoom.getTheme().getValue())
                .count(gameRoom.getQuestionCount() / 2)
                .quizLevel(gameRoom.getQuizLevel())
                .quizType(quizType)
                .build();
    }
    public Set<GameRoomParticipant> getAllRoomParticipant(String roomId){
        GameRoom gameRoom = gameRoomMap.get(roomId);
        return gameRoom.getParticipants();
    }
    //방안의 회원 아이디를 통하여 회원 이름 찾기
    public String getNameById(String id){
        return idToNameMap.get(id);
    }
    public  GameRoomStatus getGameRoomStatus(String roomId) {
         return gameRoomMap.get(roomId).getStatus();
    }
    public void changeGameRoomPlaying(String roomId) {
        gameRoomMap.get(roomId).setStatus(GameRoomStatus.PLAYING);

    }

}
