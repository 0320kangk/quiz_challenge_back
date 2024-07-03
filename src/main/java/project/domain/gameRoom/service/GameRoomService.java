package project.domain.gameRoom.service;

import org.springframework.stereotype.Service;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.GameRoomDto;
import project.domain.gameRoom.model.enumerate.GameRoomStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameRoomService {
    //게임방을 어떻게 찾지?,
    //게임 방 번호와 나
    //key : room
    //key는 무엇? 방장의 id?.
    //
    private final Map<Long, GameRoom> gameRoomMap = new ConcurrentHashMap<>();
    private static long roomKey = 0L;
    //room을 만들
    /*
    방 리스트에서는
    모든 방을 출력해줄꺼야,
    들어갔어
    메시지는 roomid와 작성자를 통해 보낼꺼야

    방번호 : 방
     */
    public GameRoom createGameRoom(GameRoomDto gameRoomDto) {
        GameRoom gameRoom = GameRoom.builder()
                .id(++roomKey)
                .name(gameRoomDto.getRoomName())
                .questionCount(gameRoomDto.getQuestionCount())
                .status(GameRoomStatus.WAITING)
                .hostEmail(gameRoomDto.getEmailId())
                .build();
        if(!gameRoomMap.containsKey(roomKey)){
           return gameRoom;
        }
        throw new IllegalArgumentException("방 생성 에러, roomId가 겹칩니다.");
    }
}
