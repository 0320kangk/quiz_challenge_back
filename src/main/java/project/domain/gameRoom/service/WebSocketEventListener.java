package project.domain.gameRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.domain.gameRoom.model.domain.GameRoom;
import project.domain.gameRoom.model.dto.GameRoomHostIdDto;
import project.domain.security.jwt.util.SecurityUtil;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final GameRoomService gameRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);
        GenericMessage generic = (GenericMessage) accessor.getHeader("simpConnectMessage");
        Map<String, List<String>> nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");
        log.info("nativeHeaders data {}", nativeHeaders);
        String roomId =  nativeHeaders.get("roomId").get(0);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        GameRoom gameRoom = gameRoomService.addGameRoom(roomId, sessionId);
        log.info("test sessionId : {}", sessionId); // 방안의 사용자 웹소켓 id
        log.info("test roomId : {}", roomId);  // 방 아이디
        String userName = SecurityUtil.getCurrentUsername().orElseThrow(() -> new NoSuchElementException("user Principal 이 없습니다."));
        log.info("test user name {}", userName);
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String roomId = gameRoomService.getRoomId(sessionId);
        GameRoom gameRoom = gameRoomService.leaveGameRoom(roomId, sessionId);
        GameRoomHostIdDto gameRoomHostIdDto = new GameRoomHostIdDto(gameRoom.getHostId());
        log.info("[Disconnected] websocket session id : {}", sessionId);

        messagingTemplate.convertAndSend("/subscribe/notification/room/"+ roomId ,gameRoomHostIdDto); //방장이 누군지 알려야 함

    }
}
